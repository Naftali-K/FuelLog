package com.example.fuellog.DBRoom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fuellog.models.Transport

/**
 * @Author: naftalikomarovski
 * @Date: 2026/02/06
 */

@Dao
interface TransportDAO {

    @Query("SELECT * FROM ${Transport.TABLE_NAME}")
    fun getAllTransport(): List<Transport>

    @Insert
    fun addTransport(transport: Transport)
}