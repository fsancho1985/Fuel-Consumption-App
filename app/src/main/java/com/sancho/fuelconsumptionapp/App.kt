package com.sancho.fuelconsumptionapp

import android.app.Application
import com.sancho.fuelconsumptionapp.model.AppDatabase

class App : Application() {
    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}