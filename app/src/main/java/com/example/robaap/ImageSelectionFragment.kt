package com.example.robaap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class ImageSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Atur RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_images)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = ImageSelectionAdapter(6)

        // Atur logika untuk tombol "analyze"
        val analyzeButton = view.findViewById<Button>(R.id.btn_analyze)
        analyzeButton.setOnClickListener {
            // PERUBAHAN DI SINI: Menggunakan ID yang sudah dirapikan
            findNavController().navigate(R.id.action_imageSelectionFragment_to_analysisFragment)
        }
    }
}