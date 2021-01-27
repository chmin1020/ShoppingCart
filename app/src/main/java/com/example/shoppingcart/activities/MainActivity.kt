package com.example.shoppingcart.activities

import android.os.Bundle
import android.text.Editable
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
        viewModel = ViewModelProvider(this, viewModelFactory!!).get<roomViewModel>(roomViewModel::class.java)


        //데이터베이스의 값이 바뀌면 관찰하여 바로 update 시켜준다.
        viewModel!!.getAllBigList()?.observe(this, object : Observer<List<bigList?>?> {
            override fun onChanged(memos: List<bigList?>?) {
                bigListAdapter.update(viewModel!!.getAllBigList()?.getValue())
            }
        })

    }


    //텍스트와쳐의 메소드 오버라이드(글자가 바뀔 때 실행될 코드)
    fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        bigListAdapter.getFilter().filter(et_search.text)
    }

    fun afterTextChanged(s: Editable?) {}
}
