package com.example.fuellog.models

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/21
 */

class FuelConsumption(
    var id: Int,
    var transportID: Int,
    var date: Long,
    var kilometers: Float = 0f,
    var liters: Float = 0f,
    var fuelPrice: Float = 0f
) {
    override fun toString(): String {
        return "FuelConsumption(id=$id, transportID=$transportID, date=$date, kilometers=$kilometers, liters=$liters, fuelPrice=$fuelPrice)"
    }
}