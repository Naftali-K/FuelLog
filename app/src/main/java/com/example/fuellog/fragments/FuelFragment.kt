package com.example.fuellog.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fuellog.R

class FuelFragment : Fragment() {

    private val TAG: String = "Test_code"

    companion object {
        const val FRAGMENT_TAG = "FuelFragment"
        const val TRANSPORT_ID = "TransportId"
    }

    private var transportID: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_fuel, container, false)

        transportID = arguments?.getString(TRANSPORT_ID)
//        Log.d(TAG, "onCreateView: FuelFragment Transport ID: $transportID")

        return view
    }
}