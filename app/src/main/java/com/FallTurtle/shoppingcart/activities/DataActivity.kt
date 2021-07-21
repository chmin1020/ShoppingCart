package com.FallTurtle.shoppingcart.activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.FallTurtle.shoppingcart.MVVM.roomViewModel
import com.FallTurtle.shoppingcart.R
import com.FallTurtle.shoppingcart.adapter.smallListAdapter
import com.FallTurtle.shoppingcart.item.CustomDialog
import com.FallTurtle.shoppingcart.item.bigList
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

        //리사이클러뷰의 어댑터 및 레이아웃 설정
        spList.adapter = smallListAdapter
        spList.layoutManager = LinearLayoutManager(this)

        //뷰모델 설정(내부 데이터베이스를 효율적으로 활용하기 위함)
        if (viewModelFactory == null)
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory(this.application)
        viewModel = ViewModelProvider(this, viewModelFactory!!).get(roomViewModel::class.java)

        //아이템 추가 시 클릭 이벤트
        ib_add.setOnClickListener {
            val tmp: String = et_item.text.toString()
            if (tmp != "") {
                smallListAdapter.insert(tmp)
                et_item.text.clear()
            }
        }

        //내용 저장 시 이벤트
        btn_save.setOnClickListener {
            val date = Date(System.currentTimeMillis())
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val cur = sdf.format(date)

            viewModel!!.insert(
                bigList(
                    et_title.text.toString(), cur,
                    smallListAdapter.itemList, smallListAdapter.checkList
                )
            )
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onBackPressed() {
        val dialog = CustomDialog(this, "저장하시겠습니까?")

        dialog.setOnPositiveClickListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked() {
                val date = Date(System.currentTimeMillis())
                val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
                val cur = sdf.format(date)

                viewModel!!.insert(
                    bigList(
                        et_title.text.toString(), cur,
                        smallListAdapter.itemList, smallListAdapter.checkList
                    )
                )
                Toast.makeText(this@DataActivity, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
        dialog.setOnNegativeClickListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked() {
                Toast.makeText(this@DataActivity, "작성이 취소되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        dialog.create()
    }
}