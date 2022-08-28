package com.FallTurtle.shoppingcart.etc

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.FallTurtle.shoppingcart.R

/**
 * 쇼핑 리스트 항목들의 swipe 기능을 도와주는 helpers
 */
class SwipeHelperCallBack : ItemTouchHelper.Callback(){
    //--------------------------------------------
    // 변수 영역
    //

    private var currentDx = 0f // 현재 x 값
    private var clamp = 0f  //고정시킬 크기


    //--------------------------------------------
    // ItemTouchHelper.Callback 오버라이딩 함수들
    //

    /* 활성화된 이동 방향을 정의하는 플래그를 반환해주는 함수 */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, LEFT or RIGHT) //드래그는 0, 스와이프는 왼쪽 - 오른쪽
    }

    /* 드래그 동작의 내용을 정의하는 함수, 이 앱에서는 드래그 동작이 존재하지 않음 */
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    /* 스와이프 동작의 내용을 정의하는 함수, 이 앱에서 스와이프는 위치 이동 말고는 다른 내용이 없음 */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    /* 아이템을 터치하거나 스와이프하는 등 뷰에 변화가 생길 경우 호출되는 함수 */
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        //만약 스와이프 동작이 인식되었다면...
        if (actionState == ACTION_STATE_SWIPE) {
            //스와이프 뷰 가져오기
            val view = getView(viewHolder)

            //뷰의 고정 여부 가져오기
            val isClamped = getTag(viewHolder)

            //뷰의 현재 위치(Horizontal) 가져오기
            val newX = clampViewPositionHorizontal(dX, isClamped, isCurrentlyActive)

            //고정 한계점을 넘었다면 스와이프 뷰를 스와이프한 위치대로 이동시키는 애니메이션 구현
            if (newX == -clamp) {
                view.animate().translationX(-clamp).setDuration(100L).start()
                return
            }
            currentDx = newX

            //그대로 그리기
            getDefaultUIUtil().onDraw(c, recyclerView, view, newX, dY, actionState, isCurrentlyActive)
        }
    }

    /* 사용자가 뷰를 swipe 했다고 간주할 최소 속도를 반환하는 함수 */
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float = defaultValue * 10

    /* 사용자가 뷰를 swipe 했다고 간주할 한계 위치를 반환하는 함수 (사용자가 손을 떼면 호출됨) */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        setTag(viewHolder, currentDx <= -clamp)
        return 2f
    }


    //--------------------------------------------
    // 함수 영역
    //

    /* 위치 고정 여부의 기준점 역할을 할 clamp 설정을 하는 함수 */
    fun setClamp(clamp: Float) { this.clamp = clamp }

    /* 각 뷰홀더의 고정 여부를 태그로 지정 또는 확인하는 함수
        - isClamped = true : 고정, false : 고정 해제 */
    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) { viewHolder.itemView.tag = isClamped }
    private fun getTag(viewHolder: RecyclerView.ViewHolder) : Boolean =  viewHolder.itemView.tag as? Boolean ?: false

    /* swipe_view 만 이동시키기 위해 그 뷰를 반환하는 함수 */
    private fun getView(viewHolder: RecyclerView.ViewHolder) : View = viewHolder.itemView.findViewById(R.id.swipe_view)

    /* swipe_view 를 스와이프하고 위치 고정하는 함수 */
    private fun clampViewPositionHorizontal(dX: Float, isClamped: Boolean, isCurrentlyActive: Boolean) : Float {
        // RIGHT 최소 위치
        val max = 0f

        // 고정할 수 있으면
        val newX = if (isClamped) {
            if (isCurrentlyActive) //아직 사용자가 drag 중
                dX - clamp
            else -clamp //아니면 고정
        }
        // 고정할 수 없으면 newX는 스와이프한 만큼
        else dX/2

        // newX가 0보다 작은지 확인 (오른쪽 이동 방지)
        return newX.coerceAtMost(max)
    }
}