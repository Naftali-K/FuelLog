package com.example.fuellog.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuellog.models.TempData
import com.example.fuellog.models.Transport

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/03
 */

class TransportListViewModel: ViewModel() {
    private val transportList: MutableLiveData<List<Transport>> = MutableLiveData()
    private val isCurrentTransportDeleted: MutableLiveData<Boolean> = MutableLiveData()

    fun thisTransportList(): LiveData<List<Transport>> {
        return transportList
    }

    fun isThisTransportDeleted(): LiveData<Boolean> {
        return isCurrentTransportDeleted
    }



    fun getTransportList() {
        val transports: List<Transport> = TempData.transportList
        transportList.value = transports
    }

    fun deleteThisTransport(id: String?) {
        if (id == null) {
            return
        }

        val idInt: Int = id.toInt()
        val removedItem = TempData.transportList.removeAt(idInt)

        if (removedItem == null) {
            return
        }

        isCurrentTransportDeleted.value = true
    }
}