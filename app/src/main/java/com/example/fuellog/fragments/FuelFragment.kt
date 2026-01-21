package com.example.fuellog.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.fuellog.R
import com.example.fuellog.adapters.FuelConsumptionRecyclerViewAdapter
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.viewModels.FuelFragmentViewModel

class FuelFragment : Fragment() {

    private val TAG: String = "Test_code"

    companion object {
        const val FRAGMENT_TAG = "FuelFragment"
        const val TRANSPORT_ID = "TransportId"
    }

    private lateinit var addFuelConsumptionIv: ImageView
    private lateinit var openChartFuelConsumptionIv: ImageView
    private lateinit var fuelConsumptionRecyclerView: RecyclerView

    private var transportID: String? = null
    private lateinit var adapter: FuelConsumptionRecyclerViewAdapter

    private lateinit var viewModel: FuelFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_fuel, container, false)
        setReferences(view)
        setAdapter()
        setViewModel()

        transportID = arguments?.getString(TRANSPORT_ID)
//        Log.d(TAG, "onCreateView: FuelFragment Transport ID: $transportID")
        viewModel.getThisTransportFuel(transportID)

        return view
    }

    private fun setReferences(view: View) {
        addFuelConsumptionIv = view.findViewById(R.id.add_fuel_consumption_iv)
        openChartFuelConsumptionIv = view.findViewById(R.id.open_chart_fuel_consumption_iv)
        fuelConsumptionRecyclerView = view.findViewById(R.id.fuel_consumption_recycler_view)
    }

    private fun setAdapter() {
        adapter = FuelConsumptionRecyclerViewAdapter()
        fuelConsumptionRecyclerView.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(FuelFragmentViewModel::class.java)

        viewModel.thisTransportFuel().observe(this, Observer<List<FuelConsumption>> { item ->
            if (item == null) {
                return@Observer
            }

            adapter.setFuelConsumptionList(item)
        })
    }
}