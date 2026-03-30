package com.example.fuellog.DBRoom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fuellog.models.FuelConsumption

/**
 * @Author: naftalikomarovski
 * @Date: 2026/02/06
 */

@Dao
interface FuelConsumptionDAO {

    /**
     * Get list of all Fuel Consumption of all transports
     * @return List<FuelConsumption>
     */
    @Query("SELECT * FROM ${FuelConsumption.TABLE_NAME}")
    suspend fun getAllFuelConsumption(): List<FuelConsumption>


    /**
     * Get list of all Fuel Consumption for transport by ID
     * @param transportID Int transport ID
     * @return List<FuelConsumption>
     */
    @Query("SELECT * FROM ${FuelConsumption.TABLE_NAME} WHERE transportID=:transportID ORDER BY date DESC")
    suspend fun getTransportFuelConsumption(transportID: Int): List<FuelConsumption>


    /**
     * Add new Fuel Consumption for transport
     * @param fuelConsumption obj FuelConsumption
     * @return Long: >0 - success, item ID; -1 - IGNORE, conflict, Error
     */
    @Insert
    suspend fun addTransportFuelConsumption(fuelConsumption: FuelConsumption): Long


    /**
     * Update new Fuel Consumption for transport
     * @param fuelConsumption obj FuelConsumption
     * @return Int: 1 - success, 0 - item not find, error
     */
    @Update
    suspend fun updateTransportFuelConsumption(fuelConsumption: FuelConsumption): Int


    /**
     * Delete new Fuel Consumption for transport
     * @param fuelConsumption obj FuelConsumption
     * @return Int: >1 - deleted more than 1 item; 1 - success delete item; 0 - item not find
     */
    @Delete
    suspend fun deleteTransportFuelConsumption(fuelConsumption: FuelConsumption): Int

    /**
     * Add new Fuel Consumption for transport
     * @param id Int Fuel Consumption ID
     * @return Int: 1 - success delete item, 0 - item not find
     */
    @Query("DELETE FROM ${FuelConsumption.TABLE_NAME} WHERE id=:id")
    suspend fun deleteTransportFuelConsumptionById(id: Int): Int
}