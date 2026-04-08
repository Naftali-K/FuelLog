package com.example.fuellog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: naftalikomarovski
 * @Date: 2026/04/07
 */


/**
 * Entity for save Other Expenses for transport
 * Using DB Room
 *
 * @property id Long, Other Expenses ID
 * @property transportID Long, transportID Transport ID (link to the transport table)
 * @property titleExpenses String, Name/title of expenses
 * @property description String, description of expenses
 * @property date Long, Date of expenses (timestamp in milliseconds)
 * @property price Float, price of expenses
 */
@Entity
data class OtherExpenses(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var id: Long,
    @ColumnInfo var transportID: Long,
    @ColumnInfo var titleExpenses: String,
    @ColumnInfo var description: String?,
    @ColumnInfo var date: Long,
    @ColumnInfo var price: Float = 0f
) {
    companion object {
        const val TABLE_NAME = "OtherExpenses"
    }

    override fun toString(): String {
        return "OtherExpenses(id=$id, transportID=$transportID, titleExpenses='$titleExpenses', description='$description', date=$date, price=$price)"
    }


}