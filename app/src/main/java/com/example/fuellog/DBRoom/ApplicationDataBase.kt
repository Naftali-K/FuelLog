package com.example.fuellog.DBRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fuellog.R
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.models.Transport

/**
 * @Author: naftalikomarovski
 * @Date: 2026/02/06
 */

@Database(entities = [Transport::class, FuelConsumption::class], version = 1, exportSchema = false)
abstract class ApplicationDataBase: RoomDatabase() {
    abstract fun transportDAO(): TransportDAO
    abstract fun fuelConsumptionDAO(): FuelConsumptionDAO

//    companion object {
//        @Volatile
//        private var INSTANCE: TransportDataBase? = null
//
//        fun getINSTANCE(context: Context): TransportDataBase {
//            if (INSTANCE != null) {
//                return INSTANCE!!
//            }
//
//            INSTANCE = Room.databaseBuilder(context.applicationContext, TransportDataBase::class.java, "Transport").build()
//
//            return INSTANCE!!
//        }
//    }

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDataBase? = null

        fun getInstance(context: Context): ApplicationDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext, ApplicationDataBase::class.java, context.getString(R.string.app_name))
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}