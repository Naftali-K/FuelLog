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

    /**
     * Get transport by transport ID
     * @param id Int transport ID
     * @return Transport by ID
     */
    @Query("SELECT * FROM ${Transport.TABLE_NAME} WHERE id=:id")
    suspend fun getTransportByID(id: Int): Transport


    /**
     * Get all transport
     * @return List<Transport>
     */
    @Query("SELECT * FROM ${Transport.TABLE_NAME}")
    suspend fun getAllTransport(): List<Transport>


    /**
     * Add new transport
     * @param transport obj Transport
     * @return Long: >0 - success, item ID; -1 - IGNORE, conflict, Error
     */
    @Insert
    suspend fun addTransport(transport: Transport): Long


    /**
     * Update transport
     * @param transport obj Transport
     * @return Int: 1 - success, 0 - item not find, error
     */
    @Update
    suspend fun updateTransport(transport: Transport): Int


    /**
     * Delete transport
     * @param transport obj Transport
     * @return Int: >1 - deleted more than 1 item; 1 - success delete item; 0 - item not find
     */
    @Delete
    suspend fun deleteTransport(transport: Transport)


    /**
     * Delete transport by ID
     * @param id Transport ID
     * @return Int: 1 - success delete item, 0 - item not find
     */
    @Query("DELETE FROM ${Transport.TABLE_NAME} WHERE id=:id")
    suspend fun deleteTransportByID(id: Int): Int
}