package com.example.fuellog.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.models.TempData

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/21
 */

class FuelFragmentViewModel: ViewModel() {

    private val fuelCurrentTransport: MutableLiveData<List<FuelConsumption>> = MutableLiveData()

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
}