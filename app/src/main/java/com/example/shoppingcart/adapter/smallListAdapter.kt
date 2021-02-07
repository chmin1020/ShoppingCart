package com.example.shoppingcart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppingcart.R
import java.util.*

class smallListAdapter : RecyclerView.Adapter<smallListAdapter.CustomViewHolder>() {
    var itemList : ArrayList<String>? = ArrayList()
    var checkList : ArrayList<String>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.small_list, parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if(itemList!=null && checkList != null) {
            holder.tv_name.text = itemList!![position]
            holder.cb_check.isChecked = checkList!![position] == "T"

            holder.iv_delete.setOnClickListener { delete(holder.adapterPosition) }
            holder.cb_check.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked == true) checkList!![position] = "T" else checkList!![position] = "F"
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    fun insert(s: String) {
        itemList?.add(s)
        checkList?.add("F")
        notifyDataSetChanged()
    }

    fun delete(position: Int) {
        try {
            itemList?.removeAt(position)
            checkList?.removeAt(position)
            notifyItemRemoved(position)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

    inner class CustomViewHolder internal constructor(itemView: View) : ViewHolder(itemView) {
        var cb_check: CheckBox = itemView.findViewById(R.id.cb_check)
        var tv_name: TextView = itemView.findViewById(R.id.tv_name)
        var iv_delete: ImageView = itemView.findViewById(R.id.iv_delete)
    }
}