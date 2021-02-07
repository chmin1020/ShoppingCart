package com.example.shoppingcart.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.MVVM.roomViewModel
import com.example.shoppingcart.R
import com.example.shoppingcart.adapter.bigListAdapter
import com.example.shoppingcart.item.bigList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.big_list.view.*

class MainActivity : AppCompatActivity() {
    //database
    private var viewModel: roomViewModel? = null
    private var viewModelFactory: AndroidViewModelFactory? = null
    val bigListAdapter: bigListAdapter = bigListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spList.adapter = bigListAdapter
        spList.layoutManager = LinearLayoutManager(this)

        //뷰모델 설정(내부 데이터베이스를 효율적으로 활용하기 위함)
        if (viewModelFactory == null) {
            viewModelFactory = AndroidViewModelFactory(this.getApplication())
        }
        viewModel = ViewModelProvider(this, viewModelFactory!!).get(roomViewModel::class.java)

        val listObserver = Observer<List<bigList>> {
            bigListAdapter.update(viewModel!!.getAllBigList().value)
        }
        viewModel!!.getAllBigList().observe(this, listObserver)

        //새로운 쇼핑리스트 추가 버튼 클릭
        fab_add.setOnClickListener {
            val intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }

        //리스트 아이템 내 삭제 버튼 클릭
        bigListAdapter.setItemClickListener(object: bigListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity).setTitle("")
                    .setMessage("정말 삭제하시겠습니까?")
                dialog.setPositiveButton("예") { dialog, which ->
                    viewModel!!.delete(bigListAdapter.getItemByPosition(position))
                }
                dialog.setNegativeButton("아니오") { dialog, which -> }
                dialog.create().show()
            }
        })

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                bigListAdapter.filter.filter(et_search.text)
            }
        })
    }
}

