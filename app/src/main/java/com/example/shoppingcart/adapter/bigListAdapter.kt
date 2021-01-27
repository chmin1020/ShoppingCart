package com.example.shoppingcart.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppingcart.R
import com.example.shoppingcart.activities.DataActivity
import com.example.shoppingcart.item.bigList
import java.util.*

class bigListAdapter : RecyclerView.Adapter<bigListAdapter.CustomViewHolder>(),
    Filterable {
    //리사이클러뷰를 이루는 리스트 데이터를 저장하는 곳
    private var UfList: List<bigList?>? = null
    private var FList: List<bigList?>? = null

    //뷰홀더가 만들어질 때 실행될 코드( *뷰홀더란? -> 리스트 항목 하나의 뷰를 만들고 보존하는 역할을 한다.)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.big_list, parent, false)
        return CustomViewHolder(v) //이 리사이클러뷰에서 customviewholder를 관리하는 뷰홀더가 된다.
    }

    //리스트 항목 뷰를 뷰홀더와 결합하는 역할의 코드
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //인덱스는 리스트인덱스+1, 타이틀은 제목을 가져옴
        holder.tv_title.setText(FList!![position]?.getTitle())
        holder.tv_date.setText(FList!![position]?.getDate())
        holder.itemView.getTag(position)
        holder.itemView.setOnClickListener { v -> //아이템을 누르면 인텐트를 통해 내용 확인 란으로 이동
            val context = v.context

            //클릭 시 해당되는 데이터베이스 값들을 모두 dataActivity로 보낸다.
            val intent = Intent(context, DataActivity::class.java)
            intent.putExtra("id", FList!!.get(position)?.getId())
            intent.putExtra("title", FList!!.get(position)?.getTitle())
            intent.putExtra("date", FList!!.get(position)?.getDate())
            intent.putExtra("itemList", FList!!.get(position)?.getList())
            intent.putExtra("checkList", FList!!.get(position)?.getList2())
            context.startActivity(intent)
        }
    }

    //어댑터가 가지고 있는 항목의 개수가 몇 개인지 알려주는 메소드
    override fun getItemCount(): Int {
        return if (FList != null) FList!!.size else 0
    }

    //데이터베이스 값이 변경될 때마다 결과를 갱신해주기 위해 만든 메소드
    fun update(list: List<bigList?>?) {
        FList = list //바뀐 리스트를 받아와서
        UfList = list
        notifyDataSetChanged() //화면에서 갱신해준다.
    }

    //검색용 필터
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                FList =
                    if (constraint == null || constraint.length == 0)  //검색 창에 입력된 내용이 없을 시 전체 리스트 출력
                        UfList
                    else {
                        val filteringList: MutableList<bigList> = ArrayList<bigList>()
                        val chk = constraint.toString().trim { it <= ' ' }
                        for (i in UfList!!.indices) {  //필터되지 않은 전체 리스트에서 조건에 맞는 것만 filteringList에 추가
                            if (UfList!![i]?.getTitle()?.contains(chk)!!) {
                                UfList!![i]?.let { filteringList.add(it) }
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

    //이 리사이클러에서 사용할 뷰홀더를 정의한 클래스
    class CustomViewHolder internal constructor(itemView: View) : ViewHolder(itemView) {
        //menu_item.xml의 값들을 받아서 할당해준다.
        var tv_title: TextView
        var tv_date: TextView

        init {
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_date = itemView.findViewById(R.id.tv_date)
        }
    }
}