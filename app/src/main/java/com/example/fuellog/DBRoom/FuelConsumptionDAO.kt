package com.example.fuellog.DBRoom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fuellog.models.FuelConsumption

/**
 * @Author: naftalikomarovski
 * @Date: 2026/02/06
 */

@Dao
interface FuelConsumptionDAO {
    @Query("SELECT * FROM ${FuelConsumption.TABLE_NAME}")
    suspend fun getAllFuelConsumption(): List<FuelConsumption>

    @Query("SELECT * FROM ${FuelConsumption.TABLE_NAME} WHERE transportID=:transportID")
    suspend fun getTransportFuelConsumption(transportID: Int): List<FuelConsumption>

    @Insert
    suspend fun addTransportFuelConsumption(fuelConsumption: FuelConsumption): Long

    @Query("DELETE FROM ${FuelConsumption.TABLE_NAME} WHERE id=:id")
    suspend fun deleteTransportFuelConsumptionById(id: Int): Int
}