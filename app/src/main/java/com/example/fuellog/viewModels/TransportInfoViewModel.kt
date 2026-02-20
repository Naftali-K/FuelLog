package com.example.fuellog.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuellog.DBRoom.ApplicationDataBase
import com.example.fuellog.DBRoom.TransportDAO
import com.example.fuellog.models.Transport
import kotlinx.coroutines.launch

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/06
 */

class TransportInfoViewModel: ViewModel() {

    private lateinit var context: Context
    private lateinit var transportDAO: TransportDAO

    private val currentTransport: MutableLiveData<Transport?> = MutableLiveData()

    fun initViewModel(context: Context) {
        this.context = context
        transportDAO = ApplicationDataBase.getInstance(context).transportDAO()
    }

    fun thisTransport(): LiveData<Transport?> {
        return currentTransport
    }

    fun getThisTransport(id: String?) {
        if (id == null) {
            return
        }

        val idInt: Int = id.toInt()

        viewModelScope.launch {
            val transport = transportDAO.getTransportByID(idInt)

            currentTransport.value = transport
        }
    }
}