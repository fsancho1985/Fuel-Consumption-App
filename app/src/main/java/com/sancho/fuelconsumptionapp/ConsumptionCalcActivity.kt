package com.sancho.fuelconsumptionapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ConsumptionCalcActivity : AppCompatActivity() {
    
    private lateinit var editKm: EditText
    private lateinit var editFuel: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption_calc)
        
        editKm = findViewById(R.id.edit_km)
        editFuel = findViewById(R.id.edit_fuel)
        
        val btnCalculate: Button = findViewById(R.id.btn_calculate)
        btnCalculate.setOnClickListener { 
            if (!formValidate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val km = editKm.text.toString().toInt()
            val fuel = editFuel.text.toString().toDouble()

            val averageConsumption = calculateAverage(km, fuel)
            val formatedConsumption = "%.2f".format(averageConsumption).toDouble()

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_message))
                .setMessage(getString(R.string.consumption_message, formatedConsumption))
                .setPositiveButton(R.string.ok, object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                    }
                }).create().show()
        }
    }

    private fun calculateAverage(km: Int, fuel: Double): Double {
        return km.toDouble() / fuel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_history) {
            finish()
//            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun openListActivity() {
//        val intent = Intent(this, ListConsumption::class.java)
//
//    }

    private fun formValidate(): Boolean {
        return (editKm.text.isNotEmpty()
                && editFuel.text.isNotEmpty())
    }
}