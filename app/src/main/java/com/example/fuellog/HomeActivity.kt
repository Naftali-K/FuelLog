package com.example.fuellog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {

    lateinit var fuelConsumptionBtn: Button
    lateinit var transportListBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        setReferences()

        fuelConsumptionBtn.setOnClickListener { view ->
            startActivity(Intent(this, FuelConsumptionActivity::class.java))
        }

        transportListBtn.setOnClickListener { view ->
            startActivity(Intent(this, TransportListActivity::class.java))
        }
    }

    fun setReferences() {
        fuelConsumptionBtn = findViewById(R.id.fuel_consumption_btn)
        transportListBtn = findViewById(R.id.transport_list_btn)
    }
}