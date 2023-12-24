package com.sancho.fuelconsumptionapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class AverageCalc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "fuel_type") val fuelType: String,
    @ColumnInfo(name = "average") val average: Double,
    @ColumnInfo(name = "created_at") val createdAt: Date = Date(),
)
