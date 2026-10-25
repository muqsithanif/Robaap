package com.example.robaap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar

class EssayEditorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_essay_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temukan toolbar berdasarkan ID
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)



        // --- Bagian yang Disesuaikan ---

        // 1. Kenali semua tombol aksi
        val chatAiButton = view.findViewById<Button>(R.id.btn_chat_ai)
        val saveButton = view.findViewById<Button>(R.id.btn_save)
        val checkGrammarButton = view.findViewById<Button>(R.id.btn_check_grammar)

        // 2. Atur logika untuk tombol Chat AI
        chatAiButton.setOnClickListener {
            val chatBottomSheet = ChatAiBottomSheetFragment()
            chatBottomSheet.show(parentFragmentManager, "ChatAiBottomSheetFragment")
        }

        // 3. Atur logika untuk tombol Save (untuk sementara menampilkan pesan)
        saveButton.setOnClickListener {
            Toast.makeText(context, "Fitur 'Save' akan ditambahkan nanti", Toast.LENGTH_SHORT).show()
        }

        // 4. Atur logika untuk tombol Check Grammar
        checkGrammarButton.setOnClickListener {
            // PERINTAH NAVIGASI KE HALAMAN GRAMMAR CHECK
            findNavController().navigate(R.id.action_essayEditorFragment_to_grammarCheckFragment)
        }
    }
}