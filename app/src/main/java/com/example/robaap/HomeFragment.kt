package com.example.robaap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    // Inisialisasi ViewModel untuk fragment ini
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menampilkan layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Logika Tombol Navigasi ---
        val startSessionButton = view.findViewById<Button>(R.id.btn_start_session)
        startSessionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_missionSelectionFragment)
        }

        // --- Logika untuk Data Dinamis ---
        // 1. Kenali semua TextView status dari layout
        val connectionTextView = view.findViewById<TextView>(R.id.tv_status_connection)
        val batteryTextView = view.findViewById<TextView>(R.id.tv_status_battery)
        val temperatureTextView = view.findViewById<TextView>(R.id.tv_status_temperature)
        val humidityTextView = view.findViewById<TextView>(R.id.tv_status_humidity)

        // 2. "Observe" atau "pantau" setiap perubahan data dari ViewModel
        viewModel.isConnected.observe(viewLifecycleOwner) { isConnected ->
            connectionTextView.text = if (isConnected) "Connected" else "Disconnected"
        }

        viewModel.batteryLevel.observe(viewLifecycleOwner) { battery ->
            batteryTextView.text = "Battery: $battery%"
        }

        viewModel.temperature.observe(viewLifecycleOwner) { temp ->
            temperatureTextView.text = "Temp: $temp°C"
        }

        viewModel.humidity.observe(viewLifecycleOwner) { humidity ->
            humidityTextView.text = "Humidity: $humidity%"
        }

        // 3. Mulai simulasi pembaruan data
        viewModel.simulateDataUpdates()
    }
}