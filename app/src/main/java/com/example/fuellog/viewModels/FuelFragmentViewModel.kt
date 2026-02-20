package com.example.fuellog.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuellog.DBRoom.ApplicationDataBase
import com.example.fuellog.DBRoom.TransportDAO
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.models.TempData

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/21
 */

class FuelFragmentViewModel: ViewModel() {

    private lateinit var context: Context
    private lateinit var transportDAO: TransportDAO

    private val fuelCurrentTransport: MutableLiveData<List<FuelConsumption>> = MutableLiveData()

    fun initViewModel(context: Context) {
        this.context = context
        transportDAO = ApplicationDataBase.getInstance(context).transportDAO()
    }

    fun thisTransportFuel(): LiveData<List<FuelConsumption>> {
        return fuelCurrentTransport
    }

    fun getThisTransportFuel(id: String?) {

        if (id == null) {
            return
        }

        val idInt: Int = id.toInt()
        val fuelList = TempData.fuelConsumptionList
        fuelCurrentTransport.value = fuelList
    }






    private val isAddedNewFuelConsumption: MutableLiveData<Boolean> = MutableLiveData()

    fun isAddedFuelConsumption(): LiveData<Boolean> {
        return isAddedNewFuelConsumption
    }

    fun addNewFuelConsumption(item: FuelConsumption) {
        if (item == null) {
            return
        }

        val addedNewFuelConsumption = TempData.fuelConsumptionList.add(item)
        isAddedNewFuelConsumption.value = addedNewFuelConsumption
    }

    private val isUpdatedCurrentFuelConsumption: MutableLiveData<Boolean> = MutableLiveData()

    fun isUpdatedFuelConsumption(): LiveData<Boolean> {
        return isUpdatedCurrentFuelConsumption
    }

    fun updateFuelConsumption(id: String, item: FuelConsumption) {
        if (id == null || id.isEmpty()) {
            return
        }

        val currentFuelConsumption = TempData.fuelConsumptionList.get(id.toInt())
            currentFuelConsumption.date = item.date
            currentFuelConsumption.kilometers = item.kilometers
            currentFuelConsumption.liters = item.liters
            currentFuelConsumption.fuelPrice = item.fuelPrice

        isUpdatedCurrentFuelConsumption.value = true
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
        val removedItem = TempData.fuelConsumptionList.removeAt(idInt)

        if (removedItem == null) {
            return
        }

        isCurrentFuelConsumptionDeleted.value = true
    }
}