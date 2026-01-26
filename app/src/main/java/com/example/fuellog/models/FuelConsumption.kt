package com.example.fuellog.models

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/21
 */

class FuelConsumption(
    var id: Int,
    var transportID: Int,
    var date: Long,
    var kilometers: Float,
    var liters: Float,
    var fuelPrice: Float = 0f
) {

}