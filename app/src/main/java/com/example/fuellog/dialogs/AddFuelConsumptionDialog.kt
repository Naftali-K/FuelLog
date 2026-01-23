package com.example.fuellog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

    private lateinit var closeBtnIv: ImageView

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

        return builder.create()
    }

    private fun setReferences(view: View?) {

        if (view == null) {
            return
        }

        closeBtnIv = view.findViewById(R.id.close_btn_iv)
    }
}