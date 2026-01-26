package com.example.fuellog.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.fuellog.R

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/23
 */

class AddFuelConsumptionDialog(transportID: String): DialogFragment() {

    companion object {
        const val DIALOG_TAG = "AddFuelConsumptionDialog"
    }

    private val TAG: String = "Test_code"

    private lateinit var closeBtnIv: ImageView
    private lateinit var fuelEt: EditText
    private lateinit var distanceEt: EditText
    private lateinit var fuelPriceEt: EditText
    private lateinit var datePickerTv: TextView
    private lateinit var addBtn: Button

    private val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val millisecond = calendar.get(Calendar.MILLISECOND)

        Log.d(TAG, "setDateListener: Selected Year: $year -> Month: $month -> Day: $dayOfMonth -> Millisecond: $millisecond")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View? = activity?.layoutInflater?.inflate(R.layout.dialog_add_fuel_consumption, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        setReferences(view)

        closeBtnIv.setOnClickListener {
            dismiss()
        }

        datePickerTv.setOnClickListener {
            openDatePicker()
        }



        return builder.create()
    }

    private fun setReferences(view: View?) {

        if (view == null) {
            return
        }

        closeBtnIv = view.findViewById(R.id.close_btn_iv)
        fuelEt = view.findViewById(R.id.fuel_et)
        distanceEt = view.findViewById(R.id.distance_et)
        fuelPriceEt = view.findViewById(R.id.fuel_price_et)
        datePickerTv = view.findViewById(R.id.date_picker_tv)
        addBtn = view.findViewById(R.id.add_btn)
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()

        DatePickerDialog(requireActivity(), dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DAY_OF_MONTH)).show()
    }
}