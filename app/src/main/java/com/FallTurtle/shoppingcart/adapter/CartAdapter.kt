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
import com.FallTurtle.shoppingcart.model.Cart

/**
 * 메인 화면의 쇼핑 리스트를 담당하는 어댑터.
 * 이 어댑터는 Filterable 인터페이스를 구현해서 내용 검색이 가능하게 한다.
 */
class CartAdapter : RecyclerView.Adapter<CartAdapter.CustomViewHolder>(),
    Filterable {
    //리사이클러뷰를 이루는 리스트 데이터를 저장하는 곳
    private val unFilteredList = mutableListOf<Cart>()
    private val filteredList = mutableListOf<Cart>()

    //--------------------------------------------
    // 커스텀 뷰홀더 클래스

    //이 리사이클러에서 사용할 뷰홀더를 정의한 클래스
    inner class CustomViewHolder(private val binding: BigListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart){
            //표시할 제목과 날짜 설정
            binding.tvTitle.text = cart.title
            binding.tvDate.text = cart.date

            binding.root.setOnClickListener {
                val intent = Intent(it.context, DataActivity::class.java)
                intent.putExtra("isEdit", true)
                intent.putExtra("cart", filteredList[position])
                it.context.startActivity(intent)
            }
        }
    }


    //--------------------------------------------
    // 리사이클러뷰 어댑터 오버라이딩 함수

    //뷰홀더가 만들어질 때 실행될 코드(*뷰홀더란? -> 리스트 항목 하나의 뷰를 만들고 보존하는 역할을 한다.)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.big_list, parent, false)
        return CustomViewHolder(BigListBinding.bind(itemView))
    }

    //리스트 항목 뷰를 만들어진 뷰홀더와 결합하는 역할의 코드
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    //어댑터가 가지고 있는 항목의 개수가 몇 개인지
    override fun getItemCount(): Int {
        return filteredList.size
    }

    //데이터베이스 값이 변경될 때마다 결과를 갱신해주기 위해 만든 메소드
    fun update(list: List<Cart>) {
        filteredList.clear()
        filteredList.addAll(list)
        unFilteredList.clear()
        unFilteredList.addAll(list)

        notifyDataSetChanged() //화면에서 갱신해준다
    }


    //--------------------------------------------
    // Filterable 인터페이스 오버라이딩 함수

    //검색용 필터
    override fun getFilter(): Filter {
        return object : Filter() {
            //filtering 결과 만들기
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                filteredList.clear()
                filteredList.addAll(
                    if (constraint == null || constraint.isEmpty())  //검색 창에 입력된 내용이 없을 시 전체 리스트 출력
                        unFilteredList
                    else {
                        val chk = constraint.toString().trim { it <= ' ' }
                        unFilteredList.filter { it.title.contains(chk) }
                    })

                return FilterResults().also { it.values = filteredList }
            }

            //완성된 filterResults를 출력
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                notifyDataSetChanged()
            }
        }
    }
}
