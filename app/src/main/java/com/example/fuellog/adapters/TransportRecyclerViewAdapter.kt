package com.example.fuellog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fuellog.R
import com.example.fuellog.interfaces.AdapterActionListener
import com.example.fuellog.models.Transport

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/03
 */

class TransportRecyclerViewAdapter(
    val context: Context,
    val callBack: AdapterActionListener,
    private var transportList: List<Transport> = ArrayList<Transport>()
): RecyclerView.Adapter<TransportRecyclerViewAdapter.TransportRecyclerViewHolder>() {

    fun setTransportList(list: List<Transport>) {
        this.transportList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_transport_item, parent, false)
        return TransportRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransportRecyclerViewHolder, position: Int) {
        holder.bind(transportList.get(position), position, context, callBack)
    }

    override fun getItemCount(): Int {
        return transportList.size
    }

    class TransportRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemLinearLayout: LinearLayout
        val nameTv: TextView
        val companyTv: TextView
        val modelTv: TextView
        val yearTv: TextView
        val descriptionTv: TextView

        init {
            itemLinearLayout = itemView.findViewById(R.id.item_linear_layout)
            nameTv = itemView.findViewById(R.id.name_tv)
            companyTv = itemView.findViewById(R.id.company_tv)
            modelTv = itemView.findViewById(R.id.model_tv)
            yearTv = itemView.findViewById(R.id.year_tv)
            descriptionTv = itemView.findViewById(R.id.description_tv)
        }

        fun bind(transport: Transport, position: Int, context: Context, callBack: AdapterActionListener) {
            nameTv.text = transport.name
            companyTv.text = transport.company
            modelTv.text = transport.model
            yearTv.text = transport.year.toString()
            descriptionTv.text = transport.description

            itemLinearLayout.setOnClickListener { view ->
//                Toast.makeText(context, "This is a on click", Toast.LENGTH_SHORT).show()
                callBack.openItemIdInt(position)
            }

            itemLinearLayout.setOnLongClickListener {
//                Toast.makeText(context, "This is a long click", Toast.LENGTH_SHORT).show()
//                callBack.openItemIntBottomSheetDialog(position)
                callBack.openItemIntBottomSheetDialog(transport.id)
                true
            }
        }
    }
}