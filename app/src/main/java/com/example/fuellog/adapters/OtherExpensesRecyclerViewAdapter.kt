package com.example.fuellog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fuellog.R
import com.example.fuellog.interfaces.AdapterActionListenerNew
import com.example.fuellog.models.OtherExpenses
import com.example.fuellog.models.PublicMethods

/**
 * @Author: naftalikomarovski
 * @Date: 2026/04/20
 */
class OtherExpensesRecyclerViewAdapter(
    private var itemList: List<OtherExpenses> = ArrayList<OtherExpenses>(),
    private val callback: AdapterActionListenerNew<OtherExpenses>
): RecyclerView.Adapter<OtherExpensesRecyclerViewAdapter.OtherExpensesRecyclerViewHolder>() {

    private lateinit var contextParent: Context

    fun setOtherExpensesItemList(itemList: List<OtherExpenses>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherExpensesRecyclerViewHolder {
        contextParent = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_other_item, parent, false)
        return OtherExpensesRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherExpensesRecyclerViewHolder, position: Int) {
        holder.bind(itemList.get(position), contextParent, position, callback)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    class OtherExpensesRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var itemLinearLayout: LinearLayout
        private lateinit var dateTv: TextView
        private lateinit var titleActionTv: TextView
        private lateinit var descriptionTv: TextView
        private lateinit var priceTv: TextView

        init {
            itemLinearLayout = itemView.findViewById(R.id.item_linear_layout)
            dateTv = itemView.findViewById(R.id.date_tv)
            titleActionTv = itemView.findViewById(R.id.title_action_tv)
            descriptionTv = itemView.findViewById(R.id.description_tv)
            priceTv = itemView.findViewById(R.id.price_tv)
        }

        fun bind(otherExpenses: OtherExpenses, contextParent: Context, position: Int, callback: AdapterActionListenerNew<OtherExpenses>) {

            itemLinearLayout.setOnLongClickListener {
                callback.openItemBottomSheetDialog(otherExpenses)
                true
            }

            val dateString = PublicMethods.getDateByStringFormat(contextParent.getString(R.string.date_format), otherExpenses.date)
            dateTv.text = dateString

            titleActionTv.text = otherExpenses.titleExpenses
            descriptionTv.text = otherExpenses.description

            priceTv.text = String.format("%.2f", otherExpenses.price)
        }
    }
}