package com.example.fuellog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/21
 */

/**
 * Entity for save Fuel Consumption for transport
 * Using DB Room
 *
 * @property id Long, Unique record identifier (automatically generated)
 * @property transportID Int, transportID Transport ID (link to the transport table)
 * @property date Long, Refueling date (timestamp in milliseconds)
 * @property kilometers Float, Mileage (km)
 * @property liters Float, Amount of fuel (liters)
 * @property fuelPrice Float, Fuel price per liter
 */
@Entity
class FuelConsumption(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var id: Long = 0,
    @ColumnInfo var transportID: Int,
    @ColumnInfo var date: Long,
    @ColumnInfo var kilometers: Float = 0f,
    @ColumnInfo var liters: Float = 0f,
    @ColumnInfo var fuelPrice: Float = 0f
) {

    companion object {
        const val TABLE_NAME = "FuelConsumption"
    }

    override fun toString(): String {
        return "FuelConsumption(id=$id, transportID=$transportID, date=$date, kilometers=$kilometers, liters=$liters, fuelPrice=$fuelPrice)"
    }
}