package com.example.fuellog.viewModels

import android.content.Context
import android.util.Log
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
 * @Date: 2025/12/05
 */

class AddEditTransportViewModel: ViewModel() {

    private val TAG: String = "Test_code"
    private lateinit var context: Context
    private lateinit var db: ApplicationDataBase
    private lateinit var transportDAO: TransportDAO
    private val isFocusName: MutableLiveData<Boolean> = MutableLiveData()
    private val isFocusCompany: MutableLiveData<Boolean> = MutableLiveData()
    private val isFocusModel: MutableLiveData<Boolean> = MutableLiveData()
    private val isValidInput: MutableLiveData<Transport?> = MutableLiveData(null)
    private val currentTransport: MutableLiveData<Transport?> = MutableLiveData()
    private val isCurrentTransportUpdated: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isCurrentTransportDeleted: MutableLiveData<Boolean> = MutableLiveData()
    val isAddedNewTransport: MutableLiveData<Boolean> = MutableLiveData()

    fun initViewModel(context: Context) {
        this.context = context
        db = ApplicationDataBase.getInstance(context)
        transportDAO = db.transportDAO()
    }

    fun isFocusNameEt(): LiveData<Boolean> {
        return isFocusName
    }

    fun isFocusCompanyEt(): LiveData<Boolean> {
        return isFocusCompany
    }

    fun isFocusModelEt(): LiveData<Boolean> {
        return isFocusModel
    }

    fun isValidThisInput(): LiveData<Transport?> {
        return isValidInput
    }

    fun isAddedUpdatedThisNewTransport(): LiveData<Boolean> {
        return isAddedNewTransport
    }

    fun thisTransport(): LiveData<Transport?> {
        return currentTransport
    }

    fun isThisTransportUpdated(): LiveData<Boolean> {
        return isCurrentTransportUpdated
    }

    fun isThisTransportDeleted(): LiveData<Boolean> {
        return isCurrentTransportDeleted
    }

    fun inputValidation(nameTransport: String, companyName: String, modelTransport: String, yearMadeString: String, descriptionTransport: String) {
        if (nameTransport == null || nameTransport.isEmpty()) {
            isFocusName.value = true
            return
        }

        if (companyName == null || companyName.isEmpty()) {
            isFocusCompany.value = true
            return
        }

        if (modelTransport == null || modelTransport.isEmpty()) {
            isFocusModel.value = true
            return
        }

        var yearMade: Int = 0
        if (yearMadeString != null && !yearMadeString.equals("")) {
            yearMade = yearMadeString.toInt()
        }

        val transport = Transport(0, nameTransport, companyName, modelTransport, yearMade, descriptionTransport)

        isValidInput.value = transport
    }

    fun addNewTransport(nameTransport: String, companyName: String, modelTransport: String, yearMade: Int, descriptionTransport: String) {
        val transport = Transport(0, nameTransport, companyName, modelTransport, yearMade, descriptionTransport)

        addNewTransport(transport)
    }

    fun addNewTransport(transport: Transport) {
        viewModelScope.launch {
            val transportID = transportDAO.addTransport(transport)
            Log.d(TAG, "addNewTransport: Added transport ID: $transportID")

            if (transportID < 0) {
                isAddedNewTransport.value = false
                return@launch
            }

            isAddedNewTransport.value = true
        }
    }

    fun getThisTransport(id: String?) {
        if (id == null) {
            return
        }

        val idInt: Int = id.toInt()
        val transport = TempData.transportList.get(idInt)

        currentTransport.value = transport
    }

    fun updateThisTransport(id: String?, transport: Transport) {
        if (id == null) {
            return
        }

        val idInt: Int = id.toInt()
        val transportOriginal = TempData.transportList.get(idInt)

        transportOriginal.name = transport.name
        transportOriginal.company = transport.company
        transportOriginal.model = transport.model
        transportOriginal.year = transport.year
        transportOriginal.description = transport.description

        isCurrentTransportUpdated.value = true
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