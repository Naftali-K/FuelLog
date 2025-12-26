package com.example.fuellog

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val TAG = "Test_code"
    lateinit var versionCodeTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setReferences()
        setVersionToView()
        nextPage()
    }

    fun setReferences() {
        versionCodeTv = findViewById(R.id.version_code_tv)
    }

    fun setVersionToView() {
        val manager = this.packageManager;
        val info = manager.getPackageInfo(this.packageName, PackageManager.GET_ACTIVITIES)
        Log.d(TAG, "setVersionToView: Version name: ${info.versionName} Version Code: ${info.versionCode}")

        val finalString = getString(R.string.version_xxx).replace("***", info.versionName.toString())
        versionCodeTv.text = finalString
    }

    fun nextPage() {
        Handler(Looper.getMainLooper()).postDelayed(object: Runnable {
            override fun run() {
                val intent = Intent(baseContext, HomeActivity::class.java);
                startActivity(intent)
                finish()
            }
        }, 1000)
    }
}