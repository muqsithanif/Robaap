package com.example.robaap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.webrtc.*

class ControlFragment : Fragment() {

    // BARU: Variabel untuk WebRTC dan Firebase
    private lateinit var peerConnectionFactory: PeerConnectionFactory
    private var peerConnection: PeerConnection? = null
    private lateinit var surfaceViewRenderer: SurfaceViewRenderer
    private val iceServers = ArrayList<PeerConnection.IceServer>()
    private val database = Firebase.database.reference
    private val roomRef = database.child("rooms").child("room_webrtc_test") // Ganti dengan ID ruangan dinamis nantinya

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // BARU: Inisialisasi semua komponen
        initWebRTC(view)
        startSignaling()

        // --- Logika Tombol Finish (Tetap sama) ---
        val finishButton = view.findViewById<Button>(R.id.btn_finish)
        finishButton.setOnClickListener {
            findNavController().navigate(R.id.action_controlFragment_to_imageSelectionFragment)
        }
    }

    // BARU: Fungsi untuk inisialisasi WebRTC
    private fun initWebRTC(view: View) {
        // Inisialisasi Factory
        val options = PeerConnectionFactory.InitializationOptions.builder(requireContext())
            .createInitializationOptions()
        PeerConnectionFactory.initialize(options)
        peerConnectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory()

        // Inisialisasi STUN server
        iceServers.add(PeerConnection.IceServer.builder("stun:stun.l.google.com:19302").createIceServer())

        // Inisialisasi SurfaceViewRenderer dari layout
        surfaceViewRenderer = view.findViewById(R.id.surface_view_renderer)
        surfaceViewRenderer.init(peerConnectionFactory.eglBase.eglBaseContext, null)
        surfaceViewRenderer.setMirror(false) // Set ke false untuk video dari kamera eksternal
    }

    // BARU: Fungsi untuk memulai proses signaling dengan Firebase
    private fun startSignaling() {
        // Buat PeerConnection
        peerConnection = createPeerConnection()

        // Mendengarkan 'offer' dari Jetson
        roomRef.child("offer").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val sdpData = snapshot.getValue(HashMap::class.java)
                    val sdpDescription = sdpData?.get("sdp") as? String
                    if (sdpDescription != null) {
                        Log.d("ControlFragment", "Offer received")
                        val offer = SessionDescription(SessionDescription.Type.OFFER, sdpDescription)
                        peerConnection?.setRemoteDescription(SdpObserverImpl("SetRemoteOffer"), offer)
                        createAnswer()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("ControlFragment", "Firebase offer listener cancelled", error.toException())
            }
        })

        // Mendengarkan 'candidates' dari Jetson
        roomRef.child("candidates").child("sender").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    val candidateData = snapshot.getValue(HashMap::class.java)
                    val sdp = candidateData?.get("candidate") as? String
                    val sdpMLineIndex = (candidateData?.get("sdpMLineIndex") as? Long)?.toInt()
                    val sdpMid = candidateData?.get("sdpMid") as? String
                    if (sdp != null && sdpMLineIndex != null && sdpMid != null) {
                        val candidate = IceCandidate(sdpMid, sdpMLineIndex, sdp)
                        peerConnection?.addIceCandidate(candidate)
                        Log.d("ControlFragment", "ICE candidate from sender added")
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // BARU: Fungsi untuk membuat PeerConnection
    private fun createPeerConnection(): PeerConnection? {
        val rtcConfig = PeerConnection.RTCConfiguration(iceServers)
        val observer = object : PeerConnection.Observer {
            override fun onSignalingChange(p0: PeerConnection.SignalingState?) {}
            override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {}
            override fun onIceConnectionReceivingChange(p0: Boolean) {}
            override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {}

            override fun onIceCandidate(candidate: IceCandidate?) {
                if (candidate != null) {
                    val candidateData = hashMapOf(
                        "sdpMLineIndex" to candidate.sdpMLineIndex,
                        "sdpMid" to candidate.sdpMid,
                        "candidate" to candidate.sdp
                    )
                    roomRef.child("candidates").child("receiver").push().setValue(candidateData)
                    Log.d("ControlFragment", "Local ICE candidate sent")
                }
            }

            override fun onAddStream(stream: MediaStream?) {}

            override fun onTrack(transceiver: RtpTransceiver?) {
                val track = transceiver?.receiver?.track()
                if (track is VideoTrack) {
                    activity?.runOnUiThread {
                        track.addSink(surfaceViewRenderer)
                        Log.d("ControlFragment", "Video track added to SurfaceViewRenderer")
                    }
                }
            }
            override fun onRemoveStream(p0: MediaStream?) {}
            override fun onDataChannel(p0: DataChannel?) {}
            override fun onRenegotiationNeeded() {}
        }
        return peerConnectionFactory.createPeerConnection(rtcConfig, observer)
    }

    // BARU: Fungsi untuk membuat Answer
    private fun createAnswer() {
        peerConnection?.createAnswer(object : SdpObserverImpl("CreateAnswer") {
            override fun onCreateSuccess(answerDescription: SessionDescription?) {
                if (answerDescription != null) {
                    peerConnection?.setLocalDescription(SdpObserverImpl("SetLocalAnswer"), answerDescription)
                    val answerSdp = hashMapOf(
                        "type" to answerDescription.type.canonicalForm(),
                        "sdp" to answerDescription.description
                    )
                    roomRef.child("answer").setValue(answerSdp)
                    Log.d("ControlFragment", "Answer created and sent")
                }
            }
        }, MediaConstraints())
    }

    // BARU: Helper class untuk SdpObserver
    open class SdpObserverImpl(private val logTag: String) : SdpObserver {
        override fun onCreateSuccess(desc: SessionDescription?) { Log.d(logTag, "onCreateSuccess") }
        override fun onSetSuccess() { Log.d(logTag, "onSetSuccess") }
        override fun onCreateFailure(error: String?) { Log.e(logTag, "onCreateFailure: $error") }
        override fun onSetFailure(error: String?) { Log.e(logTag, "onSetFailure: $error") }
    }

    // BARU: Membersihkan resource saat fragment dihancurkan
    override fun onDestroyView() {
        super.onDestroyView()
        peerConnection?.close()
        peerConnection = null
        surfaceViewRenderer.release()
        // Optional: hapus data dari Firebase saat sesi berakhir
        // roomRef.removeValue()
        Log.d("ControlFragment", "Resources released")
    }
}