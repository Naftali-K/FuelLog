package com.example.fuellog.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Bundle
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
import com.example.fuellog.models.OtherExpenses
import com.example.fuellog.models.PublicMethods

/**
 * @Author: naftalikomarovski
 * @Date: 2026/04/08
 */
class AddOtherExpensesDialog(val transportID: String, val otherExpenses: OtherExpenses? = null, val  callback: AddUpdateListener<OtherExpenses>): DialogFragment() {

    companion object {
        const val DIALOG_TAG = "AddOtherExpensesDialog"
    }
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

    private lateinit var closeBtnIv: ImageView
    private lateinit var titleExpensesEt: EditText
    private lateinit var descriptionEt: EditText
    private lateinit var priceEt: EditText
    private lateinit var datePickerTv: TextView
    private lateinit var addBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View? = activity?.layoutInflater?.inflate(R.layout.dialog_add_other_expenses, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(view)

        setReferences(view)
        setDefaultDate(PublicMethods.getCurrentMilliseconds())
        setValuesForUpdate(otherExpenses)

        closeBtnIv.setOnClickListener {
            dismiss()
        }

        datePickerTv.setOnClickListener {
            openDatePicker()
        }

        addBtn.setOnClickListener {
            addUpdateOtherExpenses()
        }

        return builder.create()
    }

    private fun setReferences(view: View?) {
        val v = view ?: return

        closeBtnIv = v.findViewById(R.id.close_btn_iv)
        titleExpensesEt = v.findViewById(R.id.title_expenses_et)
        descriptionEt = v.findViewById(R.id.description_et)
        priceEt = v.findViewById(R.id.price_et)
        datePickerTv = v.findViewById(R.id.date_picker_tv)
        addBtn = v.findViewById(R.id.add_btn)
    }

    private fun setValuesForUpdate(otherExpenses: OtherExpenses? = null) {
        if (otherExpenses == null) {
            return
        }

        titleExpensesEt.setText(otherExpenses.titleExpenses)
        descriptionEt.setText(otherExpenses.description)
        priceEt.setText(otherExpenses.price.toString())
        setDefaultDate(otherExpenses.date)

        addBtn.text = getString(R.string.update)
        isUpdate = true
    }

    private fun setDefaultDate(milliseconds: Long) {
        calendar.timeInMillis = milliseconds
        val dateString = PublicMethods.getDateByStringFormat(getString(R.string.date_format), milliseconds)
        datePickerTv.text = dateString
    }

    private fun openDatePicker() {
        DatePickerDialog(requireActivity(), dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DAY_OF_MONTH)).show()
    }

    private fun addUpdateOtherExpenses() {

        val titleExpenses = titleExpensesEt.text.toString()
        if (titleExpenses == null || titleExpenses.isEmpty()) {
            titleExpensesEt.requestFocus()
            return
        }
        val description = descriptionEt.text.toString()

        val price = priceEt.text.toString()
        var priceFloat = 0f
        if (price != null && !price.isEmpty()) {
            priceFloat = price.toFloat()
        }

        val date = calendar.timeInMillis

        if (isUpdate) {

            otherExpenses ?: return

            otherExpenses.titleExpenses = titleExpenses
            otherExpenses.description = description
            otherExpenses.price = priceFloat
            otherExpenses.date = date

            callback.update(otherExpenses)
            return
        }

        val newOtherExpenses = OtherExpenses(0, transportID.toLong(), titleExpenses, description, date, priceFloat)

        callback.add(newOtherExpenses)
        return
    }
}