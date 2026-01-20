package com.example.fuellog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fuellog.R

class OtherFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "OtherFragment"
        const val TRANSPORT_ID = "TransportId"
    }

    private var transportID: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_other, container, false)

        transportID = arguments?.getString(FuelFragment.Companion.TRANSPORT_ID)

        return view
    }
}