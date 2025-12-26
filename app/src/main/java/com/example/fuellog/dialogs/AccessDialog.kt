package com.example.fuellog.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.fuellog.R
import com.example.fuellog.interfaces.AccessCanselListener

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/26
 */

class AccessDialog(val stringInt: Int, val callback: AccessCanselListener): DialogFragment() {

    companion object {
        const val DIALOG_TAG = "AccessDialog"
    }

    lateinit var closeBtnIv: ImageView
    lateinit var notificationTv: TextView
    lateinit var accessBtn: Button
    lateinit var cancelBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View? = activity?.layoutInflater?.inflate(R.layout.dialog_access, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(view)

        setReferences(view)

        notificationTv.text = getString(stringInt)

        closeBtnIv.setOnClickListener {
            dismiss()
        }

        accessBtn.setOnClickListener {
            callback.access()
            dismiss()
        }

        cancelBtn.setOnClickListener {
            callback.cansel()
            dismiss()
        }

        return builder.create()
    }

    fun setReferences(view: View?) {
        view?.let {
            closeBtnIv = it.findViewById(R.id.close_btn_iv)
            notificationTv = it.findViewById(R.id.notification_tv)
            accessBtn = it.findViewById(R.id.access_btn)
            cancelBtn = it.findViewById(R.id.cancel_btn)
        }
    }
}