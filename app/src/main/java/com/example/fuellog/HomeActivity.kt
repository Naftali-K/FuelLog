package com.example.fuellog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fuellog.DBRoom.ApplicationDataBase

class HomeActivity : AppCompatActivity() {

    private val TAG: String = "Test_code"
    lateinit var fuelConsumptionBtn: Button
    lateinit var transportListBtn: Button
    lateinit var testBtn: Button

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

        testBtn.setOnClickListener {
//            val dao = ApplicationDataBase.getInstance(this).transportDAO()
//            val transportList = dao.getAllTransportOld()
//            Log.d(TAG, "onCreate: Transport List -> $transportList")
        }
    }

    fun setReferences() {
        fuelConsumptionBtn = findViewById(R.id.fuel_consumption_btn)
        transportListBtn = findViewById(R.id.transport_list_btn)
        testBtn = findViewById(R.id.test_btn)
    }
}