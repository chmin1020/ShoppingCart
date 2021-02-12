package com.fallTurtle.shoppingcart.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fallTurtle.shoppingcart.R
import java.util.*

//한 쇼핑리스트 내에서 아이템 목록을 담당할 어댑터
class smallListAdapter : RecyclerView.Adapter<smallListAdapter.CustomViewHolder>() {
    var itemList : ArrayList<String>? = ArrayList()
    var checkList : ArrayList<String>? = ArrayList()
    
    //각 함수에 대한 세부 설명은 생략
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.small_list, parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if(itemList!=null && checkList != null) {
            holder.tv_name.text = itemList!![position]
            holder.cb_check.isChecked = checkList!![position] == "T"
 
            //체크에 따른 취소선 표시
            if(holder.cb_check.isChecked)
                holder.tv_name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            else
                holder.tv_name.paintFlags = 0

            //아이템 삭제와 체크 변화 시 이벤트 설정
            holder.iv_delete.setOnClickListener { delete(holder.adapterPosition) }
            holder.cb_check.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkList!![position] = "T"
                    holder.tv_name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                else {
                    checkList!![position] = "F"
                    holder.tv_name.paintFlags = 0
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }
    
    //아이템 추가, 기본적으로 체크는 false로 설정
    fun insert(s: String) {
        itemList?.add(s)
        checkList?.add("F")
        notifyDataSetChanged()
    }
    
    //아이템 삭제 설정
    private fun delete(position: Int) {
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