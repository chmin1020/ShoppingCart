package com.FallTurtle.shoppingcart.etc

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import com.FallTurtle.shoppingcart.databinding.CustomDialogBinding

/**
 * 아이템 저장 혹은 삭제 여부를 묻기 위해 사용되는 커스텀 다이얼로그.
 * 글자의 가독성을 높이기 위해 기존 alertDialog 대신 이를 사용한다.
 */
class CustomDialog(context : Context, message : String) {
    private val dialog = Dialog(context)
    private val msg = message
    private lateinit var onPositiveClickListener: ButtonClickListener
    private lateinit var onNegativeClickListener: ButtonClickListener

    private val binding by lazy { CustomDialogBinding.inflate((context as Activity).layoutInflater) }

    interface ButtonClickListener{
        fun onClicked()
    }

    fun setOnPositiveClickListener(listener: ButtonClickListener){
        onPositiveClickListener = listener
    }

    fun setOnNegativeClickListener(listener: ButtonClickListener){
        onNegativeClickListener = listener
    }


    fun create(){
        dialog.setContentView(binding.root)

        //다이얼로그 크기 및 취소 설정
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        //다이얼로그 메시지 설정
        binding.tvMessage.text = msg

        //yes 버튼 리스너 설정
        binding.tvYes.setOnClickListener{
            onPositiveClickListener.onClicked()
            dialog.dismiss()
        }

        //no 버튼 리스너 설정
        binding.tvNo.setOnClickListener{
            onNegativeClickListener.onClicked()
            dialog.dismiss()
        }

        //다이얼로그 화면 표시
        dialog.show()
    }
}