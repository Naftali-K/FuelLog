package com.example.fuellog.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuellog.models.TempData
import com.example.fuellog.models.Transport

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/06
 */

class TransportInfoViewModel: ViewModel() {

    private val currentTransport: MutableLiveData<Transport?> = MutableLiveData()

    fun thisTransport(): LiveData<Transport?> {
        return currentTransport
    }

    fun getThisTransport(id: String?) {
        if (id == null) {
            return
        }

        val idInt: Int = id.toInt()
        val transport = TempData.transportList.get(idInt)

        currentTransport.value = transport
    }
}