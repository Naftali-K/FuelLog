package com.example.fuellog.DBRoom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fuellog.models.Transport

/**
 * @Author: naftalikomarovski
 * @Date: 2026/02/06
 */

@Dao
interface TransportDAO {

    @Query("SELECT * FROM ${Transport.TABLE_NAME} WHERE id=:id")
    suspend fun getTransportByID(id: Int): Transport

    @Query("SELECT * FROM ${Transport.TABLE_NAME}")
    suspend fun getAllTransport(): List<Transport>

    @Insert
    suspend fun addTransport(transport: Transport): Long

    @Update
    suspend fun updateTransport(transport: Transport): Int

    @Delete
    suspend fun deleteTransport(transport: Transport)
}