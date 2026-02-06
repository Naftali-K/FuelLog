package com.example.fuellog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/21
 */

@Entity
class FuelConsumption(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var id: Int,
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