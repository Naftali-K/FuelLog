package com.example.fuellog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fuellog.R
import com.example.fuellog.dialogs.AddOtherExpensesDialog
import com.example.fuellog.interfaces.AddUpdateListener
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.models.OtherExpenses

class OtherFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "OtherFragment"
        const val TRANSPORT_ID = "TransportId"
    }

    private var transportID: String? = null

    private lateinit var addOtherExpensesIv: ImageView
    private lateinit var searchOtherExpensesIv: ImageView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var otherExpensesRecyclerView: RecyclerView
    private lateinit var dialog: AddOtherExpensesDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_other, container, false)

        transportID = arguments?.getString(FuelFragment.Companion.TRANSPORT_ID)

        setReferences(view)

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

    private fun openAddUpdateOtherExpensesDialog(otherExpenses: OtherExpenses? = null) {
        if (transportID == null) {
            return
        }

        dialog = AddOtherExpensesDialog(transportID!!, otherExpenses, object: AddUpdateListener<OtherExpenses> {
            override fun add(item: OtherExpenses) {
//                TODO("Not yet implemented")
            }

            override fun update(item: OtherExpenses) {
//                TODO("Not yet implemented")
            }
        })

        dialog.show(parentFragmentManager, AddOtherExpensesDialog.DIALOG_TAG)
    }
}