package com.FallTurtle.shoppingcart.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.FallTurtle.shoppingcart.R
import com.FallTurtle.shoppingcart.databinding.SmallListBinding
import java.util.*

/**
 * 각 데이터 추가 혹은 수정 화면의 아이템 리스트를 담당하는 어댑터.
 */
class SmallListAdapter : RecyclerView.Adapter<SmallListAdapter.CustomViewHolder>() {
    var itemList : ArrayList<String>? = ArrayList()
    var checkList : ArrayList<String>? = ArrayList()
    var isChanged = false

    //--------------------------------------------
    // 커스텀 뷰홀더 클래스
    //

    class CustomViewHolder(private val binding: SmallListBinding) : ViewHolder(binding.root) {
        fun bind(name: String, isChecked: Boolean,
                 deleteListener: View.OnClickListener, checkListener: CompoundButton.OnCheckedChangeListener){

            //체크에 따른 취소선 표시
            checkSetting(isChecked)

            //표시할 이름과 체크 여부 설정
            binding.tvName.text = name
            binding.cbCheck.isChecked = isChecked

            //check 버튼과 delete 이미지에 대한 이벤트 리스너 설정
            binding.ivDelete.setOnClickListener(deleteListener)
            binding.cbCheck.setOnCheckedChangeListener(checkListener)
        }

        fun checkSetting(isChecked: Boolean){
            if(isChecked)
                binding.tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            else
                binding.tvName.paintFlags = 0
        }
    }

    //--------------------------------------------
    // 리사이클러뷰 어댑터 오버라이딩 함수
    //

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.small_list, parent, false)
        return CustomViewHolder(SmallListBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val deleteListener = View.OnClickListener{ delete(position) }
        val checkListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            holder.checkSetting(isChecked)
            isChanged = true
        }

        holder.bind(itemList!![position], (checkList!![position] == "T"), deleteListener, checkListener)
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }


    //--------------------------------------------
    // 리스트 관리를 위한 추가적인 함수 영역
    //

    //아이템 추가, 기본적으로 체크는 false로 설정
    fun insert(s: String) {
        itemList?.add(s)
        checkList?.add("F")
        isChanged = true
        notifyItemInserted(itemCount)
    }
    
    //아이템 삭제 설정
    private fun delete(position: Int) {
        try {
            itemList?.removeAt(position)
            checkList?.removeAt(position)
            isChanged = true
            notifyItemRemoved(position)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }
}