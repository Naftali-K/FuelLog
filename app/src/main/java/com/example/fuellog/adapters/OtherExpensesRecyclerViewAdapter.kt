package com.example.fuellog.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
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

    private val TAG: String = "Test_code"
    private lateinit var contextParent: Context
    private var itemListFiltered = mutableListOf<OtherExpenses>()

    fun setOtherExpensesItemList(itemList: List<OtherExpenses>) {
//        this.itemList = itemList
//        notifyDataSetChanged()

        if (itemList != null) {
            this.itemList = itemList
            this.itemListFiltered = ArrayList(itemList)
            notifyDataSetChanged()
        }
    }

    fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object: Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults? {
            val filteredList = ArrayList<OtherExpenses>()

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(itemList)
            } else {
                for (otherExpenses: OtherExpenses in itemList) {
                    if (otherExpenses.titleExpenses.lowercase().contains(charSequence.toString().lowercase()) ||
                        otherExpenses.description?.lowercase()?.contains(charSequence.toString().lowercase()) == true) {
                        filteredList.add(otherExpenses)
                    }
                }
            }

            val filterResult = FilterResults()
            filterResult.values = filteredList

            return filterResult
        }

        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
            itemListFiltered.clear()

//            @Suppress("UNCHECKED_CAST")
//            itemListFiltered.addAll(filterResults?.values as Collection<OtherExpenses>)

            val result = filterResults?.values as? Collection<OtherExpenses>
            result?.let {
                itemListFiltered.addAll(it)
            }

            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherExpensesRecyclerViewHolder {
        contextParent = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_other_item, parent, false)
        return OtherExpensesRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherExpensesRecyclerViewHolder, position: Int) {
        holder.bind(itemListFiltered.get(position), contextParent, position, callback)
    }

    override fun getItemCount(): Int {
        return itemListFiltered.size
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