package com.example.fuellog.DBRoom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fuellog.models.OtherExpenses

/**
 * @Author: naftalikomarovski
 * @Date: 2026/04/15
 */

@Dao
interface OtherExpensesDAO {

    /**
     * Get list of all Other Expenses of all transports
     * @return List<OtherExpenses>
     */
    @Query("SELECT * FROM ${OtherExpenses.TABLE_NAME}")
    suspend fun getAllOtherExpenses(): List<OtherExpenses>


    /**
     * Get list of all Other Expenses for transport by ID
     * @param transportID Int transport ID
     * @return List<OtherExpenses>
     */
    @Query("SELECT * FROM ${OtherExpenses.TABLE_NAME} WHERE transportID=:transportID ORDER BY date DESC")
    suspend fun getTransportOtherExpenses(transportID: Long): List<OtherExpenses>


    /**
     * Add new Other Expenses for transport
     * @param otherExpenses obj OtherExpenses
     * @return Long: >0 - success, item ID; -1 - IGNORE, conflict, Error
     */
    @Insert
    suspend fun addTransportOtherExpenses(otherExpenses: OtherExpenses): Long


    /**
     * Update new Other Expenses for transport
     * @param otherExpenses obj OtherExpenses
     * @return Int: 1 - success, 0 - item not find, error
     */
    @Update
    suspend fun updateTransportOtherExpenses(otherExpenses: OtherExpenses): Int


    /**
     * Delete new Other Expenses for transport
     * @param otherExpenses obj OtherExpenses
     * @return Int: >1 - deleted more than 1 item; 1 - success delete item; 0 - item not find
     */
    @Delete
    suspend fun deleteTransportOtherExpenses(otherExpenses: OtherExpenses): Int


    /**
     * Add new Other Expenses for transport
     * @param id Int Other Expenses ID
     * @return Int: 1 - success delete item, 0 - item not find
     */
    @Query("DELETE FROM ${OtherExpenses.TABLE_NAME} WHERE id=:id")
    suspend fun deleteTransportOtherExpensesByID(id: Long): Int
}