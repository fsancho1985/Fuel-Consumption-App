package com.sancho.fuelconsumptionapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sancho.fuelconsumptionapp.model.AverageCalc

class ConsumptionCalcActivity : AppCompatActivity() {
    
    private lateinit var editKm: EditText
    private lateinit var editFuel: EditText
    private lateinit var editFuelType: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption_calc)
        
        editKm = findViewById(R.id.edit_km)
        editFuel = findViewById(R.id.edit_fuel)
        editFuelType = findViewById(R.id.auto_complete_fuel_type)

        val items = resources.getStringArray(R.array.fuel_type)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        editFuelType.setAdapter(adapter)
        
        val btnCalculate: Button = findViewById(R.id.btn_calculate)
        btnCalculate.setOnClickListener { 
            if (!formValidate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val km = editKm.text.toString().toInt()
            val fuel = editFuel.text.toString().toDouble()
            val fuelType = editFuelType.text.toString()

            val averageConsumption = calculateAverage(km, fuel)
            val formatedConsumption = "%.2f".format(averageConsumption).toDouble()

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_message, fuelType))
                .setMessage(getString(R.string.consumption_message, formatedConsumption))
                .setPositiveButton(R.string.ok, object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                    }
                }).setNegativeButton(R.string.save) {
                    dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.averageDao()
                        dao.insert(AverageCalc(fuelType = fuelType, average = formatedConsumption))

                        runOnUiThread {
                            val intent = Intent(this@ConsumptionCalcActivity, ConsumptionList::class.java)
                            intent.putExtra("teste", "teste")
                            startActivity(intent)
                        }

                        runOnUiThread {
                            Toast.makeText(
                                this@ConsumptionCalcActivity,
                                R.string.db_message,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }.start()
                }.create().show()
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
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity() {
        val intent = Intent(this@ConsumptionCalcActivity, ConsumptionList::class.java)
        startActivity(intent)

    }

    private fun formValidate(): Boolean {
        return (editKm.text.isNotEmpty()
                && editFuel.text.isNotEmpty())
    }
}