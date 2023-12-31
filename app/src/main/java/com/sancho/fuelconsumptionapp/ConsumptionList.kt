package com.sancho.fuelconsumptionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sancho.fuelconsumptionapp.model.AverageCalc
import java.text.SimpleDateFormat
import java.util.Locale

class ConsumptionList : AppCompatActivity() {

    private lateinit var rv_list_consumption: RecyclerView
    private lateinit var listAverage: MutableList<AverageCalc>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption_list)

        listAverage = mutableListOf()

        Thread {
            val app = application as App
            val dao = app.db.averageDao()
            val response = dao.getRegister()

            runOnUiThread { listAverage.addAll(response) }
        }.start()

        val adapter = ListAdapter(listAverage)

        rv_list_consumption = findViewById(R.id.rv_consumption_list)
        rv_list_consumption.adapter = adapter
        rv_list_consumption.layoutManager = LinearLayoutManager(this)

    }

    private inner class ListAdapter(
        private val listAverage: List<AverageCalc>
    ) : RecyclerView.Adapter<ListViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item, parent, false)
            return ListViewHolder(view)
        }

        override fun getItemCount(): Int {
            return listAverage.size
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val itemCurrent = listAverage[position]
            holder.bind(itemCurrent)
        }

    }

    private class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: AverageCalc) {

            val dataString: TextView = itemView.findViewById(R.id.data_result_item)
            val fuelString: TextView = itemView.findViewById(R.id.fuel_type_result_item)
            val averageString: TextView = itemView.findViewById(R.id.average_result_item)

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))

            val data = sdf.format(item.createdAt)
            val fuelType = item.fuelType
            val average = item.average

            dataString.text = "Data do abastecimento: $data"
            fuelString.text = "Tipo de combustível: $fuelType"
            averageString.text = "Média de consumo: $average km/l"



        }
    }

}