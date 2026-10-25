package com.example.robaap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar

class GrammarCheckFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grammar_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temukan toolbar berdasarkan ID
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        // Atur listener untuk ikon navigasi (panah kembali)
        toolbar.setNavigationOnClickListener {
            // Perintah untuk kembali ke halaman sebelumnya (Editor Esai)
            findNavController().popBackStack()
        }
    }
}