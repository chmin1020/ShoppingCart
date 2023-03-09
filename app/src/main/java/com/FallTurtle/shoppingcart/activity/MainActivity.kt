package com.FallTurtle.shoppingcart.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.FallTurtle.shoppingcart.viewModel.ShoppingViewModel
import com.FallTurtle.shoppingcart.adapter.CartAdapter
import com.FallTurtle.shoppingcart.databinding.ActivityMainBinding
import com.FallTurtle.shoppingcart.model.Cart
import dagger.hilt.android.AndroidEntryPoint

/**
 * 앱 첫 실행 시 나타나는 화면을 담당하는 액티비티.
 * 이 액티비티에서는 쇼핑 리스트 확인, 검색, 삭제를 할 수 있고 이를 위한 이벤트 리스너를 사용한다.
 * 또한 각 쇼핑 리스트의 아이템들을 상세하게 확인 혹은 수정할 수 있도록 DataActivity 로 넘어가는 intent 객체를 사용한다.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:ShoppingViewModel by viewModels()

    //viewBinding
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //전체적인 쇼핑 리스트를 보여줄 리사이클러뷰의 어댑터
    val cartAdapter: CartAdapter = CartAdapter()


    //---------------------------------------
    // 생명 주기 콜백 영역

    /* onCreate()에서는 리사이클러뷰, 모델을 통한 뷰 갱신 및 이벤트 리스너 설정을 수행 */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //리사이클러뷰 설정
        binding.spList.adapter = cartAdapter
        binding.spList.layoutManager = LinearLayoutManager(this)

        //LiveData, observer 기능을 통해 실시간 쇼핑 리스트 데이터 변화 감지 및 출력
        val listObserver = Observer<List<Cart>> {
            cartAdapter.update(it)
            decideShowingDescription() // 화면이 빈 경우 설명이 보이게 한다.
        }
        viewModel.items.observe(this, listObserver)

        //클릭 이벤트 설정
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        binding.etSearch.setText("")
        viewModel.getAllCarts()
    }


    //---------------------------------------
    // 내부 함수 영역

    /* 화면 내 여러가지 뷰에 이벤트 리스너를 추가하는 함수 */
    private fun initListeners(){
        //fab 추가 버튼 클릭 시 이벤트 (추가 아이템을 위한 DataActivity 페이지 실행)
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, DataActivity::class.java)
            intent.putExtra("isEdit", false) //새 리스트 아이템 추가를 원하므로 edit -> false
            startActivity(intent)
        }


        //검색 창에 내용 입력 시 이벤트 (내용 바뀔 때마다 TextWatcher 객체로 리스트에 필터링 적용)
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cartAdapter.filter.filter(binding.etSearch.text)
            }
        })
    }

    /* 쇼핑 리스트에 아이템이 있냐 없냐에 따라 관련 설명 제공 여부를 결정하는 함수 */
    private fun decideShowingDescription(){
        if(cartAdapter.itemCount > 0)
            binding.tvExplain.visibility = View.INVISIBLE
        else
            binding.tvExplain.visibility = View.VISIBLE
    }
}