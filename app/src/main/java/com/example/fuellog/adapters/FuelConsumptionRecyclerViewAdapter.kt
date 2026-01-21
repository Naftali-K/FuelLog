package com.example.fuellog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuellog.R
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.models.PublicMethods

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/21
 */

class FuelConsumptionRecyclerViewAdapter(
    private var fuelConsumptionList: List<FuelConsumption> = ArrayList<FuelConsumption>()
):
    RecyclerView.Adapter<FuelConsumptionRecyclerViewAdapter.FuelConsumptionRecyclerViewHolder>() {

    fun setFuelConsumptionList(itemList: List<FuelConsumption>) {
        this.fuelConsumptionList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelConsumptionRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_fuel_consumption_item, parent, false)
        return FuelConsumptionRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FuelConsumptionRecyclerViewHolder, position: Int) {
        holder.bind(fuelConsumptionList.get(position), position)
    }

    override fun getItemCount(): Int {
        return fuelConsumptionList.size
    }


    class FuelConsumptionRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var dateTv: TextView
        private lateinit var kilometersValueTv: TextView
        private lateinit var litersValueTv: TextView
        private lateinit var literTo100KmTv: TextView
        private lateinit var kmTo1LiterTv: TextView

        init {
            dateTv = itemView.findViewById(R.id.date_tv)
            kilometersValueTv = itemView.findViewById(R.id.kilometers_value_tv)
            litersValueTv = itemView.findViewById(R.id.liters_value_tv)
            literTo100KmTv = itemView.findViewById(R.id.liter_to_100_km_tv)
            kmTo1LiterTv = itemView.findViewById(R.id.km_to_1_liter_tv)
        }

        fun bind(fuelConsumption: FuelConsumption, position: Int) {
            dateTv.text = fuelConsumption.date.toString()
            kilometersValueTv.text = fuelConsumption.kilometers.toString()
            litersValueTv.text = fuelConsumption.liters.toString()

            literTo100KmTv.text = String.format("%.2f", PublicMethods.litersTo100Kilometers(fuelConsumption.liters.toFloat(), fuelConsumption.kilometers.toFloat()))
            kmTo1LiterTv.text = String.format("%.2f", PublicMethods.kilometersTo1Liters(fuelConsumption.liters.toFloat(), fuelConsumption.kilometers.toFloat()))
        }
    }
}