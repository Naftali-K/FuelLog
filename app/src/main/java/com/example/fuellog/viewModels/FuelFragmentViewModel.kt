package com.example.fuellog.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuellog.DBRoom.ApplicationDataBase
import com.example.fuellog.DBRoom.FuelConsumptionDAO
import com.example.fuellog.models.FuelConsumption
import kotlinx.coroutines.launch

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/21
 */

class FuelFragmentViewModel: ViewModel() {

    private val TAG: String = "Test_code"

    private lateinit var context: Context
    private lateinit var fuelConsumptionDAO: FuelConsumptionDAO

    private val fuelCurrentTransport: MutableLiveData<List<FuelConsumption>> = MutableLiveData()

    fun initViewModel(context: Context) {
        this.context = context
        fuelConsumptionDAO = ApplicationDataBase.getInstance(context).fuelConsumptionDAO()
    }

    fun thisTransportFuel(): LiveData<List<FuelConsumption>> {
        return fuelCurrentTransport
    }

    fun getThisTransportFuel(id: String?) {

        if (id == null) {
            return
        }

        val idInt: Int = id.toInt()

        viewModelScope.launch {
            val fuelList = fuelConsumptionDAO.getTransportFuelConsumption(idInt)

            if (fuelList == null) {
                return@launch
            }

            fuelCurrentTransport.value = fuelList
        }
    }






    private val isAddedNewFuelConsumption: MutableLiveData<Boolean> = MutableLiveData()

    fun isAddedFuelConsumption(): LiveData<Boolean> {
        return isAddedNewFuelConsumption
    }

    fun addNewFuelConsumption(item: FuelConsumption) {
        if (item == null) {
            return
        }

        viewModelScope.launch {
            val fuelConsumptionID = fuelConsumptionDAO.addTransportFuelConsumption(item)

            if (fuelConsumptionID < 0) {
                isAddedNewFuelConsumption.value = false
                return@launch
            }

            isAddedNewFuelConsumption.value = true
        }
    }

    private val isUpdatedCurrentFuelConsumption: MutableLiveData<Boolean> = MutableLiveData()

    fun isUpdatedFuelConsumption(): LiveData<Boolean> {
        return isUpdatedCurrentFuelConsumption
    }

    fun updateFuelConsumption(item: FuelConsumption) {

        Log.d(TAG, "updateFuelConsumption: Update Fuel Consumption: $item")

        viewModelScope.launch {
            val response = fuelConsumptionDAO.updateTransportFuelConsumption(item)

            if (response == 1) {
                isUpdatedCurrentFuelConsumption.value = true
                return@launch
            }

            isUpdatedCurrentFuelConsumption.value = false
        }
    }

    private val isCurrentFuelConsumptionDeleted: MutableLiveData<Boolean> = MutableLiveData()

    fun isFuelConsumptionDeleted(): LiveData<Boolean> {
        return isCurrentFuelConsumptionDeleted
    }

    fun deleteFuelConsumption(id: String) {
        if (id == null || id.isEmpty() ) {
            return
        }

        val idInt: Int = id.toInt()

        viewModelScope.launch {
            val response = fuelConsumptionDAO.deleteTransportFuelConsumptionById(idInt)

            if (response == 1) {
                isCurrentFuelConsumptionDeleted.value = true
                return@launch
            }

            isCurrentFuelConsumptionDeleted.value = false
        }
    }
}