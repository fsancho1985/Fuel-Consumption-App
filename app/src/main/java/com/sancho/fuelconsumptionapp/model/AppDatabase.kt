package com.sancho.fuelconsumptionapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AverageCalc::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun averageCalc(): AverageCalc

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            return if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "average_consumption"
                    ).build()
                }
                INSTANCE as AppDatabase
            } else {
                INSTANCE as AppDatabase
            }
        }
    }
}