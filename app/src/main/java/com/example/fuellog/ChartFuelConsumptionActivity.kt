package com.example.fuellog

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChartFuelConsumptionActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TRANSPORT_ID = "EXTRA_TRANSPORT_ID"
    }

    private lateinit var backBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_chart_fuel_consumption)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        setReferences()

        backBtn.setOnClickListener { view ->
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun setReferences() {
        backBtn = findViewById(R.id.back_btn)
    }
}