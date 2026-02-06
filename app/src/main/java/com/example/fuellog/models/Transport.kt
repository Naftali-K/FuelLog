package com.example.fuellog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/02
 */

@Entity
data class Transport(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var id: Int,
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