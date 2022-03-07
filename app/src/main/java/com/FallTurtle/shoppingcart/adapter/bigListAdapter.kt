package com.FallTurtle.shoppingcart.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.FallTurtle.shoppingcart.R
import com.FallTurtle.shoppingcart.activities.DataActivity
import com.FallTurtle.shoppingcart.item.bigList
import java.util.*

//메인 액티비티에서 쇼핑리스트를 보여줄 리스트 어댑터, 검색 기능을 위해 Filterable 상속받음
class bigListAdapter : RecyclerView.Adapter<bigListAdapter.CustomViewHolder>(),
    Filterable {
    /*추후 데이터 바인딩 시도 예정*/
    //리사이클러뷰를 이루는 리스트 데이터를 저장하는 곳
    private var UfList: List<bigList>? = ArrayList()
    private var FList: List<bigList>? = ArrayList()

    //뷰홀더가 만들어질 때 실행될 코드(*뷰홀더란? -> 리스트 항목 하나의 뷰를 만들고 보존하는 역할을 한다.)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.big_list, parent, false)
        return CustomViewHolder(v) //이 리사이클러뷰에서 customviewholder를 관리하는 뷰홀더가 된다.
    }

    //리스트 항목 뷰를 만들어진 뷰홀더와 결합하는 역할의 코드
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //아이템을 알맞게 가져옴
        holder.tv_title.text = FList?.get(position)?.getTitle()
        holder.tv_date.text = FList?.get(position)?.getDate()
        holder.itemView.getTag(position)

        holder.swipeView.setOnClickListener { v -> //아이템을 누르면 인텐트를 통해 내용 확인 란으로 이동
            val context = v.context
            val intent = Intent(context, DataActivity::class.java)

            intent.putExtra("isEdit", true)
            intent.putExtra("id", FList?.get(position)?.getId())
            intent.putExtra("title", FList?.get(position)?.getTitle())
            intent.putExtra("date", FList?.get(position)?.getDate())
            intent.putExtra("itemList", FList?.get(position)?.getList())
            intent.putExtra("checkList", FList?.get(position)?.getList2())
            context.startActivity(intent)
        }

        //iv_delete 클릭 시 삭제작업 요청됨
        holder.iv_delete.setOnClickListener { v ->
            itemClickListener.onClicked(v, position)
        }
    }

    //어댑터가 가지고 있는 항목의 개수가 몇 개인지 알려주는 메소드
    override fun getItemCount(): Int {
        return FList?.size ?: 0
    }

    //데이터베이스 값이 변경될 때마다 결과를 갱신해주기 위해 만든 메소드
    fun update(list: List<bigList>?) {
        FList = list //바뀐 리스트를 받아와서
        UfList = list
        notifyDataSetChanged() //화면에서 갱신해준다
    }

    //검색용 필터
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                FList =
                    if (constraint == null || constraint.isEmpty())  //검색 창에 입력된 내용이 없을 시 전체 리스트 출력
                        UfList
                    else {
                        val filteringList: MutableList<bigList> = ArrayList<bigList>()
                        val chk = constraint.toString().trim { it <= ' ' }
                        for (i in UfList?.indices!!) {  //필터되지 않은 전체 리스트에서 조건에 맞는 것만 filteringList에 추가
                            if (UfList?.get(i)?.getTitle()?.contains(chk)!!) {
                                UfList!![i].let { filteringList.add(it) }
                            }
                        }
                        filteringList
                    }
                val filterResults = FilterResults()
                filterResults.values = FList
                return filterResults
            }
            //완성된 filterResults를 출력
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                FList = results.values as ArrayList<bigList>
                notifyDataSetChanged()
            }
        }
    }
    
    //삭제 버튼 동작을 위한 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onClicked(v: View, position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener
    fun setItemClickListener(itemLongClickListener: OnItemClickListener) {
        this.itemClickListener = itemLongClickListener
    }

    //삭제를 위한 포지션에 따른 아이템 값 보내기 함수
    fun getItemByPosition(position: Int) : bigList{
        return FList?.get(position)!!
    }

    //이 리사이클러에서 사용할 뷰홀더를 정의한 클래스
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //menu_item.xml의 값들을 받아서 할당해준다.
        var tv_title: TextView = itemView.findViewById(R.id.tv_title)
        var tv_date: TextView = itemView.findViewById(R.id.tv_date)
        var iv_delete: ImageView = itemView.findViewById(R.id.iv_delete)
        var swipeView: LinearLayout = itemView.findViewById(R.id.swipe_view)
    }
}
