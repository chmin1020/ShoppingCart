package com.FallTurtle.shoppingcart.item

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.TextView
import com.FallTurtle.shoppingcart.R
import kotlinx.android.synthetic.main.custom_dialog.*

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

        //다이얼로그 크기 및 취소 설정
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        //다이얼로그 메시지 설정
        dialog.tv_message.text = msg

        //각 버튼 리스너 설정
        dialog.tv_yes.setOnClickListener{
            onPositiveClickListener.onClicked()
            dialog.dismiss()
        }
        dialog.tv_no.setOnClickListener{
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