package com.sancho.fuelconsumptionapp

import com.sancho.fuelconsumptionapp.model.AverageCalc

interface OnClickListener {
    fun onLongClick(position: Int, averageCalc: AverageCalc)
}