package com.example.fuellog.DBRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fuellog.R
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.models.OtherExpenses
import com.example.fuellog.models.Transport

/**
 * @Author: naftalikomarovski
 * @Date: 2026/02/06
 */

// https://youtu.be/YOFyhC4sE8A?si=71tV4zx3ElsfV_At - lesson video

@Database(entities = [Transport::class, FuelConsumption::class, OtherExpenses::class], version = 2, exportSchema = false)
abstract class ApplicationDataBase: RoomDatabase() {
    abstract fun transportDAO(): TransportDAO
    abstract fun fuelConsumptionDAO(): FuelConsumptionDAO
    abstract fun otherExpensesDAO(): OtherExpensesDAO

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
                    .addMigrations(MIGRATION_1_2)
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
//                super.migrate(db)
//                db.execSQL("""
//                    CREATE TABLE IF NOT EXISTS `OtherExpenses` (
//                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                    `transportId` INTEGER NOT NULL,
//                    `titleExpenses` TEXT NOT NULL,
//                    `description` TEXT,
//                    `date` INTEGER NOT NULL,
//                    `price` REAL NOT NULL)
//                    """.trimIndent())

                db.execSQL("CREATE TABLE IF NOT EXISTS `OtherExpenses` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`transportId` INTEGER NOT NULL, " +
                        "`titleExpenses` TEXT NOT NULL, " +
                        "`description` TEXT, " +
                        "`date` INTEGER NOT NULL, " +
                        "`price` REAL NOT NULL)")
            }
        }
    }
}