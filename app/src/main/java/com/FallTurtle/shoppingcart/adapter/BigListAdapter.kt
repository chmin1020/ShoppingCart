package com.FallTurtle.shoppingcart.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.FallTurtle.shoppingcart.R
import com.FallTurtle.shoppingcart.activity.DataActivity
import com.FallTurtle.shoppingcart.databinding.BigListBinding
import com.FallTurtle.shoppingcart.model.BigList
import java.util.*

/**
 * 메인 화면의 쇼핑 리스트를 담당하는 어댑터.
 * 이 어댑터는 Filterable 인터페이스를 구현해서 내용 검색이 가능하게 한다.
 */
class BigListAdapter : RecyclerView.Adapter<BigListAdapter.CustomViewHolder>(),
    Filterable {
    //리사이클러뷰를 이루는 리스트 데이터를 저장하는 곳
    private var unFilteredList: List<BigList>? = ArrayList()
    private var filteredList: List<BigList>? = ArrayList()


    //--------------------------------------------
    // 터치 이벤트를 위한 인터페이스와 관련 함수들

    private lateinit var itemClickListener : OnDeleteImgClickListener

    //삭제 버튼 동작을 위한 클릭 리스너 인터페이스
    interface OnDeleteImgClickListener {
        fun onClicked(v: View, position: Int)
    }

    fun setItemClickListener(itemLongClickListener: OnDeleteImgClickListener) {
        this.itemClickListener = itemLongClickListener
    }

    //삭제를 위한 포지션에 따른 아이템 값 보내기 함수
    fun getItemByPosition(position: Int) : BigList {
        return filteredList?.get(position)!!
    }


    //--------------------------------------------
    // 커스텀 뷰홀더 클래스
    //

    //이 리사이클러에서 사용할 뷰홀더를 정의한 클래스
    inner class CustomViewHolder(private val binding: BigListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String, date: String,
                 swipeListener: View.OnClickListener, deleteListener: View.OnClickListener){
            //표시할 제목과 날짜 설정
            binding.tvTitle.text = title
            binding.tvDate.text = date

            //swipe, delete 기능에 대한 이벤트 리스너 설정
            binding.swipeView.setOnClickListener(swipeListener)
            binding.ivDelete.setOnClickListener(deleteListener)
        }
    }


    //--------------------------------------------
    // 리사이클러뷰 어댑터 오버라이딩 함수

    //뷰홀더가 만들어질 때 실행될 코드(*뷰홀더란? -> 리스트 항목 하나의 뷰를 만들고 보존하는 역할을 한다.)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.big_list, parent, false)
        return CustomViewHolder(BigListBinding.bind(itemView)) //이 리사이클러뷰에서 customviewholder를 관리하는 뷰홀더가 된다.
    }

    //리스트 항목 뷰를 만들어진 뷰홀더와 결합하는 역할의 코드
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //아이템을 알맞게 가져옴
        val title = filteredList!![position].title ?: ""
        val date = filteredList!![position].date ?: ""

        val swipeListener = View.OnClickListener { v -> //아이템을 누르면 인텐트를 통해 내용 확인 란으로 이동
            val context = v.context
            val intent = Intent(context, DataActivity::class.java)

            intent.putExtra("isEdit", true)
            intent.putExtra("id", filteredList?.get(position)?.id)
            intent.putExtra("title", filteredList?.get(position)?.title)
            intent.putExtra("date", filteredList?.get(position)?.date)
            intent.putExtra("itemList", filteredList?.get(position)?.list)
            intent.putExtra("checkList", filteredList?.get(position)?.list2)
            context.startActivity(intent)
        }

        val deleteListener = View.OnClickListener{ itemClickListener.onClicked(it, position) }

        holder.bind(title, date, swipeListener, deleteListener)
    }

    //어댑터가 가지고 있는 항목의 개수가 몇 개인지 알려주는 메소드
    override fun getItemCount(): Int {
        return filteredList?.size ?: 0
    }

    //데이터베이스 값이 변경될 때마다 결과를 갱신해주기 위해 만든 메소드
    fun update(list: List<BigList>?) {
        filteredList = list //바뀐 리스트를 받아와서
        unFilteredList = list
        notifyDataSetChanged() //화면에서 갱신해준다
    }


    //--------------------------------------------
    // Filterable 인터페이스 오버라이딩 함수

    //검색용 필터
    override fun getFilter(): Filter {
        return object : Filter() {
            //filtering 결과 만들기
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                filteredList =
                    if (constraint == null || constraint.isEmpty())  //검색 창에 입력된 내용이 없을 시 전체 리스트 출력
                        unFilteredList
                    else {
                        val filteringList: MutableList<BigList> = ArrayList<BigList>()
                        val chk = constraint.toString().trim { it <= ' ' }
                        for (i in unFilteredList?.indices!!) {  //필터되지 않은 전체 리스트에서 조건에 맞는 것만 filteringList에 추가
                            if (unFilteredList?.get(i)?.title?.contains(chk)!!) {
                                unFilteredList!![i].let { filteringList.add(it) }
                            }
                        }
                        filteringList
                    }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            //완성된 filterResults를 출력
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList = results.values as ArrayList<BigList>
                notifyDataSetChanged()
            }
        }
    }
}
