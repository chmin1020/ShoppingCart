package com.example.shoppingcart.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.MVVM.roomViewModel
import com.example.shoppingcart.R
import com.example.shoppingcart.adapter.smallListAdapter
import com.example.shoppingcart.item.bigList
import kotlinx.android.synthetic.main.activity_record.*
import java.text.SimpleDateFormat
import java.util.*

class RecordActivity : AppCompatActivity(){
    //database
    private var viewModel: roomViewModel? = null
    private var viewModelFactory: ViewModelProvider.AndroidViewModelFactory? = null
    val smallListAdapter: smallListAdapter = smallListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

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

        val intent: Intent = getIntent()
        val id: Int = intent.getIntExtra("id",-1)
        if(id==-1){
            Toast.makeText(this,"오류 발생",Toast.LENGTH_SHORT).show()
            finish()
        }
        tv_title.setText(intent.getStringExtra("title"))
        smallListAdapter.itemList = intent.getStringArrayListExtra("itemlist")
        smallListAdapter.checkList= intent.getStringArrayListExtra("checklist")


        ib_add.setOnClickListener {
            val tmp: String = et_item.text.toString()
            if (tmp != "") {
                smallListAdapter.insert(tmp)
                et_item.text.clear()
            }
        }

        tv_save.setOnClickListener {
            val date = Date(System.currentTimeMillis())
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val cur = sdf.format(date)
            val list = bigList(tv_name.text.toString(),cur,smallListAdapter.itemList,smallListAdapter.checkList)
            list.setId(id)

            viewModel!!.insert(list)
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
