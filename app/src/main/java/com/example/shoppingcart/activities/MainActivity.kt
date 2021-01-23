package com.example.shoppingcart.activities

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingcart.R
import com.example.shoppingcart.adapter.bigListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    //텍스트와쳐의 메소드 오버라이드(글자가 바뀔 때 실행될 코드)
    fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        bigListAdapter.getFilter().filter(et_search.getText())
    }

    fun afterTextChanged(s: Editable?) {}
}
