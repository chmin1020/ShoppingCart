package com.FallTurtle.shoppingcart.activities

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
import com.FallTurtle.shoppingcart.MVVM.roomViewModel
import com.FallTurtle.shoppingcart.adapter.bigListAdapter
import com.FallTurtle.shoppingcart.databinding.ActivityMainBinding
import com.FallTurtle.shoppingcart.item.CustomDialog
import com.FallTurtle.shoppingcart.item.bigList
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    //database
    private var viewModel: roomViewModel? = null
    private var viewModelFactory: AndroidViewModelFactory? = null
    val bigListAdapter: bigListAdapter = bigListAdapter()

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spList.adapter = bigListAdapter
        binding.spList.layoutManager = LinearLayoutManager(this)

        //뷰모델 설정(내부 데이터베이스를 효율적으로 활용하기 위함)
        if (viewModelFactory == null)
            viewModelFactory = AndroidViewModelFactory(this.application)
        viewModel = ViewModelProvider(this, viewModelFactory!!).get(roomViewModel::class.java)

        //LiveData와 observe를 통해 실시간 데이터 변화 감지 및 출력
        val listObserver = Observer<List<bigList>> {
            bigListAdapter.update(viewModel!!.getAllBigList().value)
        }
        viewModel!!.getAllBigList().observe(this, listObserver)

        //새로운 쇼핑리스트 추가 버튼 클릭(fab)
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }

        //리스트 아이템 내 삭제 버튼 클릭(adapter 커스텀 인터페이스 이용)
        bigListAdapter.setItemLongClickListener(object: bigListAdapter.OnItemLongClickListener {
            override fun onClicked(v: View, position: Int) {
                val dialog = CustomDialog(this@MainActivity, "정말 삭제하시겠습니까?")

                dialog.setOnPositiveClickListener(object : CustomDialog.ButtonClickListener {
                    override fun onClicked() {
                        viewModel!!.delete(bigListAdapter.getItemByPosition(position))
                        Toast.makeText(this@MainActivity,"삭제되었습니다.",Toast.LENGTH_SHORT).show()
                    }
                })
                dialog.setOnNegativeClickListener(object : CustomDialog.ButtonClickListener {
                    override fun onClicked() {}
                })
                dialog.create()
            }
        })

        //검색 기능 textWatcher를 통해 구현
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                bigListAdapter.filter.filter(et_search.text)
            }
        })
    }
}

