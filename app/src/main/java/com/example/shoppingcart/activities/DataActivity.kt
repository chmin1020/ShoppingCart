package com.example.shoppingcart.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.MVVM.roomViewModel
import com.example.shoppingcart.R
import com.example.shoppingcart.adapter.smallListAdapter
import com.example.shoppingcart.item.bigList
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_data.ib_add
import kotlinx.android.synthetic.main.activity_data.spList
import java.text.SimpleDateFormat
import java.util.*

class DataActivity : AppCompatActivity() {
    //database
    private var viewModel: roomViewModel? = null
    private var viewModelFactory: ViewModelProvider.AndroidViewModelFactory? = null
    private val smallListAdapter: smallListAdapter = smallListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        spList.adapter = smallListAdapter
        spList.layoutManager = LinearLayoutManager(this)

        //뷰모델 설정(내부 데이터베이스를 효율적으로 활용하기 위함)
        if (viewModelFactory == null) {
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory(this.getApplication())
        }
        viewModel = ViewModelProvider(this, viewModelFactory!!).get(roomViewModel::class.java)

        ib_add.setOnClickListener {
            val tmp:String = et_item.text.toString()
            if (tmp != "") {
                smallListAdapter.insert(tmp)
                et_item.text.clear()
            }

        }

        tv_save.setOnClickListener{
            val date = Date(System.currentTimeMillis())
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val cur = sdf.format(date)

            viewModel!!.insert(
                bigList(
                    et_title.text.toString(), cur,
                    smallListAdapter.itemList, smallListAdapter.checkList)
            )
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}