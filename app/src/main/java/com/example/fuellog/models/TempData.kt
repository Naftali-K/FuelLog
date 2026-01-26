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
            FuelConsumption(0, 0, 3489043376, 454f, 42f),
            FuelConsumption(0, 0, 35354345, 547f, 48f),
            FuelConsumption(0, 0, 4353564546, 632f, 52f),
        )
    }
}