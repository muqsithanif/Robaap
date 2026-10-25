package com.example.robaap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class MissionSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menampilkan layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_mission_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fungsi untuk navigasi ke halaman kontrol
        val navigateToControl = {
            findNavController().navigate(R.id.action_missionSelectionFragment_to_controlFragment)
        }

        // Temukan semua tombol dari layout berdasarkan ID-nya
        val kitchenButton = view.findViewById<Button>(R.id.btn_mission_kitchen)
        val livingRoomButton = view.findViewById<Button>(R.id.btn_mission_living_room)
        val gardenButton = view.findViewById<Button>(R.id.btn_mission_garden)

        // Atur listener untuk setiap tombol, semuanya menuju ke tujuan yang sama
        kitchenButton.setOnClickListener { navigateToControl() }
        livingRoomButton.setOnClickListener { navigateToControl() }
        gardenButton.setOnClickListener { navigateToControl() }
    }
}