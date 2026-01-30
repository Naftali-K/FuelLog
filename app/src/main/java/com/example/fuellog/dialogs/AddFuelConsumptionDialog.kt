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
import com.example.fuellog.interfaces.AddUpdateListener
import com.example.fuellog.models.FuelConsumption
import com.example.fuellog.models.PublicMethods
import com.example.fuellog.models.TempData

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/23
 */

class AddFuelConsumptionDialog(val transportID: String, val idItemString: String? = null, val callback: AddUpdateListener<FuelConsumption>): DialogFragment() {

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
    private val calendar = Calendar.getInstance()
    private var isUpdate = false

    private val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val millisecond = calendar.timeInMillis

        val dateString = PublicMethods.getDateByStringFormat(getString(R.string.date_format), millisecond)

//        Log.d(TAG, "setDateListener: Selected Year: $year -> Month: $month -> Day: $dayOfMonth -> Millisecond: $millisecond -> Date format: $dateString")
        datePickerTv.text = dateString
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
        setDefaultDate(PublicMethods.getCurrentMilliseconds())
        setValuesForUpdate(idItemString)

        closeBtnIv.setOnClickListener {
            dismiss()
        }

        datePickerTv.setOnClickListener {
            openDatePicker()
        }

        addBtn.setOnClickListener {
            val date = calendar.timeInMillis
            val kilometers = distanceEt.text.toString()
            var kilometersFloat = 0f
            if (kilometers != null && !kilometers.isEmpty()) {
                kilometersFloat = kilometers.toFloat()
            }

            val liters = fuelEt.text.toString()
            var litersFloat = 0f
            if (liters != null && !liters.isEmpty()) {
                litersFloat = liters.toFloat()
            }

            val price = fuelPriceEt.text.toString()
            var priceFloat = 0f
            if (price != null && !price.isEmpty()) {
                priceFloat = price.toFloat()
            }

            val newFuelConsumption = FuelConsumption(0, transportID.toInt(), date, kilometersFloat, litersFloat, priceFloat)
//            Log.d(TAG, "onCreateDialog: New FuelConsumption: ${newFuelConsumption.toString()}")

            if (isUpdate) {
                callback.update(newFuelConsumption)
                return@setOnClickListener
            }

            callback.add(newFuelConsumption)
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

    private fun setDefaultDate(milliseconds: Long) {
        calendar.timeInMillis = milliseconds
        val dateString = PublicMethods.getDateByStringFormat(getString(R.string.date_format), milliseconds)
        datePickerTv.text = dateString
    }

    private fun setValuesForUpdate(idItemString: String? = null) {
        if (idItemString == null || idItemString.isEmpty()) {
            return
        }

        val idInt = idItemString.toInt()
        val item = TempData.fuelConsumptionList.get(idInt)

        fuelEt.setText(item.liters.toString())
        distanceEt.setText(item.kilometers.toString())
        fuelPriceEt.setText(item.fuelPrice.toString())
        setDefaultDate(item.date)

        addBtn.text = getString(R.string.update)

        isUpdate = true
    }

    private fun openDatePicker() {

        DatePickerDialog(requireActivity(), dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DAY_OF_MONTH)).show()
    }
}