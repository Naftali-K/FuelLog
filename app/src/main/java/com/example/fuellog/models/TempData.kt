package com.example.fuellog.models

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/03
 */

class TempData {

    companion object {
        val transportList = mutableListOf(
            Transport(0, "My Bike", "Honda", "CBR400R", 2022, "First bike"),
            Transport(0, "Rent Bike", "Yamaha", "R3", 2018, null),
            Transport(0, "Family Car", "Nissan", "Serena", 2025, "New car"),
        )

        val fuelConsumptionList = mutableListOf(
            FuelConsumption(0, 0, 1763135488301, 454f, 42f),
            FuelConsumption(0, 0, 1767135488301, 547f, 48f),
            FuelConsumption(0, 0, 1769135488301, 632f, 52f),
        )
    }
}