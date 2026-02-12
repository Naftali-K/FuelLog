package com.example.fuellog.DBRoom

import androidx.room.Dao
import androidx.room.Query
import com.example.fuellog.models.FuelConsumption

/**
 * @Author: naftalikomarovski
 * @Date: 2026/02/06
 */

@Dao
interface FuelConsumptionDAO {
    @Query("SELECT * FROM ${FuelConsumption.TABLE_NAME}")
    fun getAllFuelConsumption(): List<FuelConsumption>

//    @Query("SELECT * FROM ${FuelConsumption.TABLE_NAME} WHERE transportID=:transportID")
//    fun getTransportFuelConsumption(transportID: Int)
}