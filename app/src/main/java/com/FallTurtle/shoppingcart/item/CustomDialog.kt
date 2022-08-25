package com.FallTurtle.shoppingcart.item

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.TextView
import com.FallTurtle.shoppingcart.R
import com.FallTurtle.shoppingcart.databinding.CustomDialogBinding

class CustomDialog(context : Context, message : String) {
    private val dialog = Dialog(context)
    private val msg = message
    private lateinit var onPositiveClickListener: ButtonClickListener
    private lateinit var onNegativeClickListener: ButtonClickListener

    interface ButtonClickListener{
        fun onClicked()
    }
    fun create(){

        dialog.setContentView(R.layout.custom_dialog)

        val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
        val tvYes = dialog.findViewById<TextView>(R.id.tv_yes)
        val tvNo = dialog.findViewById<TextView>(R.id.tv_no)

        //다이얼로그 크기 및 취소 설정
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        //다이얼로그 메시지 설정
        tvMessage.text = msg

        //각 버튼 리스너 설정
        tvYes.setOnClickListener{
            onPositiveClickListener.onClicked()
            dialog.dismiss()
        }
        tvNo.setOnClickListener{
            onNegativeClickListener.onClicked()
            dialog.dismiss()
        }

        //다이얼로그 화면 표시
        dialog.show()
    }

    fun setOnPositiveClickListener(listener: ButtonClickListener){
        onPositiveClickListener = listener
    }
    fun setOnNegativeClickListener(listener: ButtonClickListener){
        onNegativeClickListener = listener
    }
}