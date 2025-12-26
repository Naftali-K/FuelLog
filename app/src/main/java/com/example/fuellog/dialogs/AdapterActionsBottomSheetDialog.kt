package com.example.fuellog.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fuellog.R
import com.example.fuellog.interfaces.AdapterActionMenuListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/18
 */

class AdapterActionsBottomSheetDialog(val callback: AdapterActionMenuListener) : BottomSheetDialogFragment() {

    lateinit var editTv: TextView
    lateinit var deleteTv: TextView
    lateinit var cancelTv: TextView


    companion object {
        const val DIALOG_TAG = "AdapterActionsBottomSheetDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.customBottomSheetDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bottom_dialog_adapter_actions, container, false)
        setReferences(view)

        editTv.setOnClickListener { view ->
            callback.editItemId()
            dismiss()
        }

        deleteTv.setOnClickListener { view ->
            callback.deleteItemId()
            dismiss()
        }

        cancelTv.setOnClickListener { view ->
            dismiss()
        }

        return view
    }

    fun setReferences(view: View) {
        editTv = view.findViewById(R.id.edit_tv)
        deleteTv = view.findViewById(R.id.delete_tv)
        cancelTv = view.findViewById(R.id.cancel_tv)
    }

}