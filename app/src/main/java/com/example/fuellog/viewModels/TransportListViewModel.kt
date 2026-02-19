package com.example.fuellog.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuellog.DBRoom.ApplicationDataBase
import com.example.fuellog.DBRoom.TransportDAO
import com.example.fuellog.models.TempData
import com.example.fuellog.models.Transport
import kotlinx.coroutines.launch

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/03
 */

class TransportListViewModel: ViewModel() {

    private lateinit var context: Context
    private lateinit var db: ApplicationDataBase
    private lateinit var transportDAO: TransportDAO
    private val transportList: MutableLiveData<List<Transport>> = MutableLiveData()
//    val transportList: LiveData<List<Transport>> = transportDAO.getAllTransport()
    private val isCurrentTransportDeleted: MutableLiveData<Boolean> = MutableLiveData()

    fun initViewModel(context: Context) {
        this.context = context
        db = ApplicationDataBase.getInstance(context)
        transportDAO = db.transportDAO()
    }

    fun thisTransportList(): LiveData<List<Transport>> {
        return transportList
    }

    fun isThisTransportDeleted(): LiveData<Boolean> {
        return isCurrentTransportDeleted
    }



    fun getTransportList() = viewModelScope.launch {
        transportList.value = transportDAO.getAllTransport()
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

    fun updateTransport(transport: Transport) = viewModelScope.launch {
        transportDAO.updateTransport(transport)
    }
}