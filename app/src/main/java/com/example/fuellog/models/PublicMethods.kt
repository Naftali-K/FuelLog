package com.example.fuellog.models

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @Author: naftalikomarovski
 * @Date: 2025/11/27
 */

class PublicMethods {

    companion object {
        fun litersTo100Kilometers(fuels: Float, distance: Float): Float {
            val consumption: Float = 100 / kilometersTo1Liters(fuels, distance)
            return consumption
        }

        fun literTo1Kilometer(fuels: Float, distance: Float): Float {
            val consumption: Float = 1 / kilometersTo1Liters(fuels, distance)
            return consumption
        }

        fun kilometersTo1Liters(fuels: Float, distance: Float): Float {
            val consumption = distance / fuels
            return consumption
        }

        fun costPerKilometer(fuels: Float, distance: Float, price: Float): Float {
            val consumptionFor1Km = literTo1Kilometer(fuels, distance)
            val cost = consumptionFor1Km * price

            return cost
        }

        fun getCurrentMilliseconds(): Long {
            val  millisecondNow: Long = System.currentTimeMillis()
            return millisecondNow
        }

        fun getCurrentYearFromMilliseconds(milliseconds: Long): Int {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            return year
        }

        fun getCurrentMonthFromMilliseconds(milliseconds: Long): Int {
            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH) + 1
            return month
        }

        fun getCurrentDayOfMonthFromMilliseconds(milliseconds: Long): Int {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            return day
        }

        fun getDateByStringFormat(formatString: String, milliseconds: Long): String {
            val year = getCurrentYearFromMilliseconds(milliseconds)
            val month = getCurrentMonthFromMilliseconds(milliseconds)
            val day = getCurrentDayOfMonthFromMilliseconds(milliseconds)

            val dateString = formatString.replace("yyyy", year.toString())
                .replace("mm", month.toString())
                .replace("dd", day.toString())

            return dateString
        }

        fun getDateByPattern(pattern: String, milliseconds: Long): String {
            val date = Date(milliseconds)

            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            val formattedDate: String = formatter.format(date)

            return formattedDate
        }
    }

}