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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fuellog.R
import com.example.fuellog.dialogs.AddOtherExpensesDialog
import com.example.fuellog.interfaces.AddUpdateListener
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.models.OtherExpenses
import com.example.fuellog.viewModels.OtherFragmentViewModel

class OtherFragment : Fragment() {

    private val TAG: String = "Test_code"
    companion object {
        const val FRAGMENT_TAG = "OtherFragment"
        const val TRANSPORT_ID = "TransportId"
    }

    private var transportID: String? = null

    private lateinit var addOtherExpensesIv: ImageView
    private lateinit var searchOtherExpensesIv: ImageView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var otherExpensesRecyclerView: RecyclerView

    private lateinit var viewModel: OtherFragmentViewModel
    private lateinit var dialog: AddOtherExpensesDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_other, container, false)

        transportID = arguments?.getString(FuelFragment.Companion.TRANSPORT_ID)

        setReferences(view)
        setViewModel()

        transportID.let {
//            viewModel.getThisTransportOtherExpenses(it)
            getOtherExpenses()
        }

        swipeRefreshLayout.setOnRefreshListener {
//            viewModel.getThisTransportOtherExpenses(transportID)
            getOtherExpenses()
        }

        addOtherExpensesIv.setOnClickListener {
            openAddUpdateOtherExpensesDialog()
        }

        return view
    }

    private fun setReferences(view: View) {
        addOtherExpensesIv = view.findViewById(R.id.add_other_expenses_iv)
        searchOtherExpensesIv = view.findViewById(R.id.search_other_expenses_iv)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        otherExpensesRecyclerView = view.findViewById(R.id.other_expenses_recycler_view)
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(OtherFragmentViewModel::class.java)
        viewModel.initViewModel(requireContext())

        viewModel.thisTransportOtherExpenses().observe(viewLifecycleOwner, Observer<List<OtherExpenses>> { item ->
            Log.d(TAG, "setViewModel: List of Other Expenses: ${item}")
            swipeRefreshLayout.isRefreshing = false
        })

        viewModel.isAddedOtherExpenses().observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
//                viewModel.getThisTransportOtherExpenses(transportID)
                getOtherExpenses()
                dialog.dismiss()

                return@Observer
            }
        })
    }

    private fun getOtherExpenses() {
        viewModel.getThisTransportOtherExpenses(transportID)
    }

    private fun openAddUpdateOtherExpensesDialog(otherExpenses: OtherExpenses? = null) {
        if (transportID == null) {
            return
        }

        dialog = AddOtherExpensesDialog(transportID!!, otherExpenses, object: AddUpdateListener<OtherExpenses> {
            override fun add(item: OtherExpenses) {
                addNewOtherExpenses(item)
            }

            override fun update(item: OtherExpenses) {
//                TODO("Not yet implemented")
            }
        })

        dialog.show(parentFragmentManager, AddOtherExpensesDialog.DIALOG_TAG)
    }

    private fun addNewOtherExpenses(otherExpenses: OtherExpenses) {
        viewModel.addThisTransportOtherExpenses(otherExpenses)
    }
}