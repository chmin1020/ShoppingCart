package com.fallTurtle.shoppingcart.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fallTurtle.shoppingcart.MVVM.roomViewModel
import com.fallTurtle.shoppingcart.R
import com.fallTurtle.shoppingcart.adapter.smallListAdapter
import com.fallTurtle.shoppingcart.item.bigList
import kotlinx.android.synthetic.main.activity_record.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class RecordActivity : AppCompatActivity(){
    //database
    private var viewModel: roomViewModel? = null
    private var viewModelFactory: ViewModelProvider.AndroidViewModelFactory? = null
    private val smallListAdapter: smallListAdapter = smallListAdapter()

    private var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        //리사이클러뷰 어댑터 및 레이아웃 설정
        spList.adapter = smallListAdapter
        spList.layoutManager = LinearLayoutManager(this)

        //뷰모델 설정(내부 데이터베이스를 효율적으로 활용하기 위함)
        if (viewModelFactory == null) {
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory(this.getApplication())
        }
        viewModel = ViewModelProvider(
            this,
            viewModelFactory!!
        ).get<roomViewModel>(roomViewModel::class.java)
        
        //리사이클러뷰 내 어떤 아이템을 표시할 지 받아온 것을 불러옴
        val intent: Intent = intent
        id = intent.getIntExtra("id",-1)
        if(id==-1){
            Toast.makeText(this,"오류 발생",Toast.LENGTH_SHORT).show()
            finish()
        }
        tv_title.text = intent.getStringExtra("title")
        smallListAdapter.itemList = intent.getStringArrayListExtra("itemList")
        smallListAdapter.checkList= intent.getStringArrayListExtra("checkList")

        //dataActivity와 마찬가지로 아이템 추가 클릭 이벤트 설정
        ib_add.setOnClickListener {
            val tmp: String = et_item.text.toString()
            if (tmp != "") {
                smallListAdapter.insert(tmp)
                et_item.text.clear()
            }
        }
    }

    override fun onBackPressed() {
        //recordActivity에서는 뒤로 버튼 시 자동 내용 갱신함
        super.onBackPressed()
        val date = Date(System.currentTimeMillis())
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val cur = sdf.format(date)
        val list = bigList(tv_title.text.toString(),cur,smallListAdapter.itemList,smallListAdapter.checkList)
        list.setId(id)

        viewModel!!.insert(list)
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }
}
