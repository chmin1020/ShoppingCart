package com.FallTurtle.shoppingcart.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.FallTurtle.shoppingcart.MVVM.RoomViewModel
import com.FallTurtle.shoppingcart.adapter.SmallListAdapter
import com.FallTurtle.shoppingcart.databinding.ActivityDataBinding
import com.FallTurtle.shoppingcart.item.CustomDialog
import com.FallTurtle.shoppingcart.item.BigList
import java.text.SimpleDateFormat
import java.util.*

class DataActivity : AppCompatActivity() {

    //edit
    private var id:Int = -1
    private var isEdit:Boolean = false
    private var firstTitle:String = ""

    //model
    private val viewModelFactory by lazy{ ViewModelProvider.AndroidViewModelFactory(this.application) }
    private val viewModel by lazy{ ViewModelProvider(this, viewModelFactory).get(RoomViewModel::class.java) }
    private val smallListAdapter: SmallListAdapter = SmallListAdapter()

    //viewBinding
    private val binding by lazy{ ActivityDataBinding.inflate(layoutInflater) }


    //---------------------------------------
    // 생명 주기 콜백 영역
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //리사이클러뷰의 어댑터 및 레이아웃 설정
        binding.spList.adapter = smallListAdapter
        binding.spList.layoutManager = LinearLayoutManager(this)

        //edit 작업인지 확인하고, 맞다면 edit 페이지 설정
        isEdit = intent.getBooleanExtra("isEdit", false)
        if(isEdit)
            editPageSetting()

        //클릭 이벤트 설정
        initListeners()
    }

    override fun onBackPressed() {
        if (smallListAdapter.isChanged || firstTitle != binding.etTitle.text.toString()) {
            val dialog = CustomDialog(this, "저장하시겠습니까?")
            dialog.setOnPositiveClickListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked() {
                    val date = Date(System.currentTimeMillis())
                    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA)
                    val cur = sdf.format(date)
                    val list = BigList(
                        binding.etTitle.text.toString(), cur,
                        smallListAdapter.itemList, smallListAdapter.checkList
                    )
                    if (isEdit)
                        list.setId(id)

                    viewModel.insert(list)
                    Toast.makeText(this@DataActivity, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
            dialog.setOnNegativeClickListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked() {
                    Toast.makeText(this@DataActivity, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
            dialog.create()
        }
        else
            finish()
    }


    //---------------------------------------
    // 내부 함수 영역
    //

    private fun initListeners(){
        binding.ibAdd.setOnClickListener {
            val tmp: String = binding.etItem.text.toString()
            if (tmp != "") {
                smallListAdapter.insert(tmp)
                binding.etItem.text.clear()
            }
        }

        //내용 저장 시 이벤트
        binding.btnSave.setOnClickListener {
            itemSaveProgress()
        }
    }

    private fun editPageSetting(){
        id = intent.getIntExtra("id",-1)
        if(id == -1) {
            Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.etTitle.setText(intent.getStringExtra("title"))
        firstTitle = binding.etTitle.text.toString()
        smallListAdapter.itemList = intent.getStringArrayListExtra("itemList")
        smallListAdapter.checkList= intent.getStringArrayListExtra("checkList")
    }

    private fun itemSaveProgress(){
        val date = Date(System.currentTimeMillis())
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA)
        val cur = sdf.format(date)

        val list = BigList(
            binding.etTitle.text.toString(),
            cur,
            smallListAdapter.itemList,
            smallListAdapter.checkList
        )
        if(id != -1)
            list.setId(id)
        viewModel.insert(list)
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }
}