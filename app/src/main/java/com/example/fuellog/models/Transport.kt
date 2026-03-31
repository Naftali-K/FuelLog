package com.example.fuellog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/02
 */

/**
 * Entity for save Transport
 * Using DB Room
 *
 * @property id Long, Unique record identifier (automatically generated)
 * @property name String, name of transport
 * @property company String, company name of transport
 * @property model String, name model of transport
 * @property year Int, year made of transport
 * @property description String, description, information, notes of transport
 */

@Entity
data class Transport(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var id: Long,
    @ColumnInfo var name: String?,
    @ColumnInfo var company: String?,
    @ColumnInfo var model: String?,
    @ColumnInfo var year: Int?,
    @ColumnInfo var description: String?
) {

    companion object {
        const val TABLE_NAME = "Transport"
    }

    override fun toString(): String {
        return "Transport(id=$id, name=$name, company=$company, model=$model, year=$year, description=$description)"
    }
}