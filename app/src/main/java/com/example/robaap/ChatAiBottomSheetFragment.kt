package com.example.robaap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChatAiBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Kita akan menghubungkan layout XML di sini nanti
        return inflater.inflate(R.layout.fragment_chat_ai_bottom_sheet, container, false)
    }
}