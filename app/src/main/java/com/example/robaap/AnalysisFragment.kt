package com.example.robaap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class AnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        // Jika Anda ingin tombol kembali di toolbar ini, tambahkan listenernya di sini
        // toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_analysis)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AnalysisAdapter(5)

        val writeEssayButton = view.findViewById<Button>(R.id.btn_write_essay)
        writeEssayButton.setOnClickListener {
            findNavController().navigate(R.id.action_analysisFragment_to_essayEditorFragment)
        }

    } // <-- Pastikan kurung kurawal ini ada (penutup untuk onViewCreated)

} // <-- Pastikan kurung kurawal ini juga ada (penutup untuk class AnalysisFragment)