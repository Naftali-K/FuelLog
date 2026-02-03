package com.example.fuellog.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.fuellog.R
import com.example.fuellog.adapters.FuelConsumptionRecyclerViewAdapter
import com.example.fuellog.dialogs.AccessDialog
import com.example.fuellog.dialogs.AdapterActionsBottomSheetDialog
import com.example.fuellog.dialogs.AddFuelConsumptionDialog
import com.example.fuellog.interfaces.AccessCanselListener
import com.example.fuellog.interfaces.AdapterActionListener
import com.example.fuellog.interfaces.AdapterActionMenuListener
import com.example.fuellog.interfaces.AddUpdateListener
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.viewModels.FuelFragmentViewModel

class FuelFragment() : Fragment() {

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
    private lateinit var dialog: AddFuelConsumptionDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_fuel, container, false)
        setReferences(view)
        setAdapter()
        setViewModel()

        transportID = arguments?.getString(TRANSPORT_ID)
//        Log.d(TAG, "onCreateView: FuelFragment Transport ID: $transportID")
        transportID.let { text ->
            viewModel.getThisTransportFuel(transportID)
        }

        addFuelConsumptionIv.setOnClickListener { v ->
            openAddUpdateFuelConsumptionDialog()
        }

        return view
    }

    private fun setReferences(view: View) {
        addFuelConsumptionIv = view.findViewById(R.id.add_fuel_consumption_iv)
        openChartFuelConsumptionIv = view.findViewById(R.id.open_chart_fuel_consumption_iv)
        fuelConsumptionRecyclerView = view.findViewById(R.id.fuel_consumption_recycler_view)
    }

    private fun setAdapter() {
        adapter = FuelConsumptionRecyclerViewAdapter(callback =  object : AdapterActionListener {
            override fun openItemIdInt(id: Int) {
//                TODO("Not yet implemented")
            }

            override fun openItemIdString(id: String) {
//                TODO("Not yet implemented")
            }

            override fun openItemIntBottomSheetDialog(id: Int) {
                openAdapterActionDialog(id)
            }

            override fun openItemStringBottomSheetDialog(id: String) {
//                TODO("Not yet implemented")
            }

        })
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

        viewModel.isAddedFuelConsumption().observe(this, Observer<Boolean> { item ->

            if (item) {
                dialog.dismiss()
                viewModel.getThisTransportFuel(transportID)
                return@Observer
            }

            Toast.makeText(context, "Some problem with add new Fuel Consumption. Try again!", Toast.LENGTH_SHORT).show()
        })

        viewModel.isUpdatedFuelConsumption().observe(this, Observer<Boolean> { item ->
            if (item) {
                dialog.dismiss()
                adapter.notifyDataSetChanged()
                return@Observer
            }

            Toast.makeText(context, "Some problem with update new Fuel Consumption. Try again!", Toast.LENGTH_SHORT).show()
        })

        viewModel.isFuelConsumptionDeleted().observe(this, Observer<Boolean> { item ->
            if (item) {
                viewModel.getThisTransportFuel(transportID)
                return@Observer
            }
        })
    }

    private fun openAddUpdateFuelConsumptionDialog(idString: String? = null) {
        if (transportID == null) {
            return
        }

        dialog = AddFuelConsumptionDialog(transportID!!, idString, callback =  object : AddUpdateListener<FuelConsumption> {
            override fun add(item: FuelConsumption) {
                Log.d(TAG, "add: New FuelConsumption: ${item}")
                addNewFuelConsumption(item)
            }

            override fun update(item: FuelConsumption) {
                Log.d(TAG, "update: Update item ID: $idString -> Item: $item")
                updateFuelConsumption(idString, item)
            }

        })

        dialog.show(parentFragmentManager, AddFuelConsumptionDialog.DIALOG_TAG)

    }

    private fun addNewFuelConsumption(item: FuelConsumption) {
        viewModel.addNewFuelConsumption(item)
    }

    private fun updateFuelConsumption(idString: String?, item: FuelConsumption) {
        if (idString == null || idString.isEmpty()) {
            return
        }

        viewModel.updateFuelConsumption(idString, item)
    }

    private fun openAdapterActionDialog(id: Int) {
        val adapterDialog = AdapterActionsBottomSheetDialog(object : AdapterActionMenuListener {
            override fun editItemId() {
                val idString = id.toString()
                openAddUpdateFuelConsumptionDialog(idString)
            }

            override fun deleteItemId() {
                openAccessDialog(id)
            }

        })

        adapterDialog.show(parentFragmentManager, AdapterActionsBottomSheetDialog.DIALOG_TAG)
    }

    private fun openAccessDialog(id: Int) {
        val dialog = AccessDialog(R.string.are_you_sure_you_want_to_delete_this, object : AccessCanselListener {
            override fun access() {
                deleteItem(id)
            }

            override fun cansel() {

            }
        })

        dialog.show(parentFragmentManager, AccessDialog.DIALOG_TAG)
    }

    private fun deleteItem(id: Int) {
        viewModel.deleteFuelConsumption(id.toString())
    }
}