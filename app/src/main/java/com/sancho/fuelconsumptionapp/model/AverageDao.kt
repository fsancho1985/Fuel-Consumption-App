package com.sancho.fuelconsumptionapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AverageDao {

    @Insert
    fun insert(averageCalc: AverageCalc)

    @Query("SELECT * FROM AverageCalc")
    fun getRegister() : List<AverageCalc>

    @Delete
    fun delete(averageCalc: AverageCalc) : Int

}