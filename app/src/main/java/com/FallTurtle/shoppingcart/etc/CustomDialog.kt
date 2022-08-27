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
    //--------------------------------------------
    // 상수 영역
    //

    //다이얼로그 객체와 표시할 메시지
    private val dialog = Dialog(context)
    private val msg = message

    //dialog view binding
    private val binding by lazy { CustomDialogBinding.inflate((context as Activity).layoutInflater) }


    //--------------------------------------------
    // 터치 이벤트를 위한 인터페이스와 관련 함수들
    //

    //각 '네', '아니오' 에 해당하는 인터페이스 구현 객체
    private lateinit var onPositiveClickListener: ButtonClickListener
    private lateinit var onNegativeClickListener: ButtonClickListener

    /* 각 이벤트 리스너 세팅 때 사용할 리스너 인터페이스 */
    interface ButtonClickListener{
        fun onClicked()
    }

    /* '네' 버튼에 해당하는 인터페이스 지정하는 함수 */
    fun setOnPositiveClickListener(listener: ButtonClickListener){
        onPositiveClickListener = listener
    }

    /* '아니오' 버튼에 해당하는 인터페이스 지정하는 함수 */
    fun setOnNegativeClickListener(listener: ButtonClickListener){
        onNegativeClickListener = listener
    }


    //--------------------------------------------
    // create 함수
    //

    /* 다이얼로그를 만들어서 화면에 출력하는 함수 */
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