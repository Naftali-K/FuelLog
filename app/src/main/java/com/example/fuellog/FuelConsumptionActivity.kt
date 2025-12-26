package com.example.fuellog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fuellog.viewModels.FuelConsumptionViewModel

class FuelConsumptionActivity : AppCompatActivity() {

    lateinit var backBtn: ImageView
    lateinit var fuelEt: EditText
    lateinit var distanceEt: EditText
    lateinit var fuelPriceEt: EditText
    lateinit var countBtn: Button
    lateinit var literTo100KmTv: TextView
    lateinit var kmTo1LiterTv: TextView
    lateinit var costPerKilometerTv: TextView

    lateinit var viewModel: FuelConsumptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_fuel_consumption)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setReferences()
        setViewModel()

        backBtn.setOnClickListener { v ->
            backPress()
        }

        countBtn.setOnClickListener { v ->
            val fuel: Float = fuelEt.text.toString().toFloat()
            val distance: Float = distanceEt.text.toString().toFloat()
            val cost: Float = fuelPriceEt.text.toString().toFloat()

            viewModel.countConsumptionKilometersTo1Liter(fuel, distance)
            viewModel.countConsumptionLitersTo100Kilometers(fuel, distance)
            viewModel.countCostPerKilometer(fuel, distance, cost)
        }
    }

    fun setReferences() {
        backBtn = findViewById(R.id.back_btn)
        fuelEt = findViewById(R.id.fuel_et)
        distanceEt = findViewById(R.id.distance_et)
        fuelPriceEt = findViewById(R.id.fuel_price_et)
        countBtn = findViewById(R.id.count_btn)
        literTo100KmTv = findViewById(R.id.liter_to_100_km_tv)
        kmTo1LiterTv = findViewById(R.id.km_to_1_liter_tv)
        costPerKilometerTv = findViewById(R.id.cost_per_kilometer_tv)
    }

    fun setViewModel() {
        viewModel = ViewModelProvider(this).get(FuelConsumptionViewModel::class.java)

        viewModel.getConsumptionLitersTo100Kilometers().observe(this, Observer<Float> { data ->
            literTo100KmTv.text = data.toString()
        })

        viewModel.getConsumptionKilometersTo1Liter().observe(this, Observer<Float> {
            kmTo1LiterTv.text = it.toString()
        })

        viewModel.getCostPerKilometer().observe(this, Observer<Float> { data ->
            costPerKilometerTv.text = data.toString()
        })
    }

    fun backPress() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }


}