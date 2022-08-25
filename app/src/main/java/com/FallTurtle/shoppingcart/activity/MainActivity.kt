package com.FallTurtle.shoppingcart.activity

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.FallTurtle.shoppingcart.MVVM.RoomViewModel
import com.FallTurtle.shoppingcart.adapter.BigListAdapter
import com.FallTurtle.shoppingcart.databinding.ActivityMainBinding
import com.FallTurtle.shoppingcart.item.CustomDialog
import com.FallTurtle.shoppingcart.item.SwipeHelperCallBack
import com.FallTurtle.shoppingcart.item.BigList

class MainActivity : AppCompatActivity() {
    //database
    private var viewModel: RoomViewModel? = null
    private var viewModelFactory: AndroidViewModelFactory? = null
    val bigListAdapter: BigListAdapter = BigListAdapter()

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
        viewModel = ViewModelProvider(this, viewModelFactory!!).get(RoomViewModel::class.java)

        //LiveData와 observe를 통해 실시간 데이터 변화 감지 및 출력
        val listObserver = Observer<List<BigList>> {
            bigListAdapter.update(viewModel!!.getAllBigList().value)
            decideDescription()
        }
        viewModel!!.getAllBigList().observe(this, listObserver)

        //새로운 쇼핑리스트 추가 버튼 클릭(fab)
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, DataActivity::class.java)
            intent.putExtra("isEdit", false)
            startActivity(intent)
        }

        //리사이클러뷰에 스와이프, 드래그 기능
        val swipeCb = SwipeHelperCallBack().apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 8)
        }
        ItemTouchHelper(swipeCb).attachToRecyclerView(binding.spList)

        //리스트 아이템 내 삭제 버튼 클릭(adapter 커스텀 인터페이스 이용)
        bigListAdapter.setItemClickListener(object : BigListAdapter.OnItemClickListener {
            override fun onClicked(v: View, position: Int) {
                val dialog = CustomDialog(this@MainActivity, "정말 삭제하시겠습니까?")

                dialog.setOnPositiveClickListener(object : CustomDialog.ButtonClickListener {
                    override fun onClicked() {
                        viewModel!!.delete(bigListAdapter.getItemByPosition(position))
                        Toast.makeText(this@MainActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
                dialog.setOnNegativeClickListener(object : CustomDialog.ButtonClickListener {
                    override fun onClicked() {}
                })
                dialog.create()
            }
        })

        //검색 기능 textWatcher를 통해 구현
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                bigListAdapter.filter.filter(binding.etSearch.text)
            }
        })
    }

    //설명 가릴까말까
    private fun decideDescription(){
        if(bigListAdapter.itemCount > 0)
            binding.tvExplain.visibility = View.INVISIBLE
        else
            binding.tvExplain.visibility = View.VISIBLE
    }
}

