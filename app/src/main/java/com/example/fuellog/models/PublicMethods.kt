package com.example.fuellog.models

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
    }

}