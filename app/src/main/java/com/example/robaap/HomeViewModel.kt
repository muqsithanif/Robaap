package com.example.robaap

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    // Data yang bisa diubah di dalam ViewModel
    private val _isConnected = MutableLiveData<Boolean>(true)
    private val _batteryLevel = MutableLiveData<Int>(92)
    private val _temperature = MutableLiveData<Int>(28)
    private val _humidity = MutableLiveData<Int>(65)

    // Data yang hanya bisa dibaca dari luar (Fragment)
    val isConnected: LiveData<Boolean> = _isConnected
    val batteryLevel: LiveData<Int> = _batteryLevel
    val temperature: LiveData<Int> = _temperature
    val humidity: LiveData<Int> = _humidity

    // Fungsi untuk mensimulasikan update data dari robot
    fun simulateDataUpdates() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                // Kurangi baterai secara acak
                _batteryLevel.value = (_batteryLevel.value ?: 0) - 1

                // Ubah suhu & kelembapan sedikit
                _temperature.value = (_temperature.value ?: 0) + Random.nextInt(-1, 2)
                _humidity.value = (_humidity.value ?: 0) + Random.nextInt(-1, 2)

                // Jika baterai habis, putuskan koneksi
                if ((_batteryLevel.value ?: 0) <= 0) {
                    _isConnected.value = false
                    _batteryLevel.value = 0
                    return // Hentikan simulasi
                }

                // Ulangi setiap 3 detik
                handler.postDelayed(this, 3000)
            }
        })
    }
}