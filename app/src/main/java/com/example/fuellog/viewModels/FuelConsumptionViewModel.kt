package com.example.fuellog.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuellog.models.PublicMethods

/**
 * @Author: naftalikomarovski
 * @Date: 2025/11/27
 */

class FuelConsumptionViewModel: ViewModel() {

    val consumptionLitersTo100Kilometers: MutableLiveData<Float> = MutableLiveData()
    val consumptionKilometersTo1Liter: MutableLiveData<Float> = MutableLiveData()
    val costPerKilometer: MutableLiveData<Float> = MutableLiveData()

    fun getConsumptionLitersTo100Kilometers(): LiveData<Float> {
        return consumptionLitersTo100Kilometers
    }

    fun getConsumptionKilometersTo1Liter(): LiveData<Float> {
        return consumptionKilometersTo1Liter
    }

    fun getCostPerKilometer(): LiveData<Float> {
        return costPerKilometer
    }

    fun countConsumptionLitersTo100Kilometers(fuels: Float, distance: Float) {
        val consumption = PublicMethods.litersTo100Kilometers(fuels, distance)
        consumptionLitersTo100Kilometers.value = consumption
    }

    fun countConsumptionKilometersTo1Liter(fuels: Float, distance: Float) {
        val consumption = PublicMethods.kilometersTo1Liters(fuels, distance)
        consumptionKilometersTo1Liter.value = consumption
    }

    fun countCostPerKilometer(fuels: Float, distance: Float, price: Float) {
        val costPerKm = PublicMethods.costPerKilometer(fuels, distance, price)
        costPerKilometer.value = costPerKm
    }
}