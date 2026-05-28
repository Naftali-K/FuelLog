package com.example.fuellog.DBRoom

import com.example.fuellog.models.FuelConsumption

class FuelLogRepository(private val fuelConsumptionDAO: FuelConsumptionDAO) {

    suspend fun getFuelConsumptionAsc(transportId: Int): List<FuelConsumption> {
        return fuelConsumptionDAO.getTransportFuelConsumptionAsc(transportId)
    }
}