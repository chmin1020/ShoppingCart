package com.FallTurtle.shoppingcart.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.FallTurtle.shoppingcart.MVVM.roomViewModel
import com.FallTurtle.shoppingcart.adapter.smallListAdapter
import com.FallTurtle.shoppingcart.databinding.ActivityDataBinding
import com.FallTurtle.shoppingcart.item.CustomDialog
import com.FallTurtle.shoppingcart.item.bigList
import java.text.SimpleDateFormat
import java.util.*

class DataActivity : AppCompatActivity() {
    //edit
    private var id:Int = -1
    private var isEdit:Boolean = false
    private var firstTitle:String = ""

    //database
    private var viewModel: roomViewModel? = null
    private var viewModelFactory: ViewModelProvider.AndroidViewModelFactory? = null
    private val smallListAdapter: smallListAdapter = smallListAdapter()

    private lateinit var binding: ActivityDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //리사이클러뷰의 어댑터 및 레이아웃 설정
        binding.spList.adapter = smallListAdapter
        binding.spList.layoutManager = LinearLayoutManager(this)

        //뷰모델 설정(내부 데이터베이스를 효율적으로 활용하기 위함)
        if (viewModelFactory == null)
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory(this.application)
        viewModel = ViewModelProvider(this, viewModelFactory!!).get(roomViewModel::class.java)

        //edit 작업인지 확인
        isEdit = intent.getBooleanExtra("isEdit", false)
        if(isEdit){
            id = intent.getIntExtra("id",-1)
            if(id==-1) {
                Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show()
                finish()
            }
            binding.etTitle.setText(intent.getStringExtra("title"))
            firstTitle = binding.etTitle.text.toString()
            smallListAdapter.itemList = intent.getStringArrayListExtra("itemList")
            smallListAdapter.checkList= intent.getStringArrayListExtra("checkList")
        }

        //아이템 추가 시 클릭 이벤트
        binding.ibAdd.setOnClickListener {
            val tmp: String = binding.etItem.text.toString()
            if (tmp != "") {
                smallListAdapter.insert(tmp)
                binding.etItem.text.clear()
            }
        }

        //내용 저장 시 이벤트
        binding.btnSave.setOnClickListener {
            val date = Date(System.currentTimeMillis())
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val cur = sdf.format(date)

            val list = bigList(
                binding.etTitle.text.toString(),
                cur,
                smallListAdapter.itemList,
                smallListAdapter.checkList
            )
            if(id != -1)
                list.setId(id)
            viewModel!!.insert(list)
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onBackPressed() {
        if (smallListAdapter.isChanged || firstTitle != binding.etTitle.text.toString()) {
            val dialog = CustomDialog(this, "저장하시겠습니까?")
            dialog.setOnPositiveClickListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked() {
                    val date = Date(System.currentTimeMillis())
                    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
                    val cur = sdf.format(date)
                    val list = bigList(
                        binding.etTitle.text.toString(), cur,
                        smallListAdapter.itemList, smallListAdapter.checkList
                    )
                    if (isEdit)
                        list.setId(id)

                    viewModel!!.insert(list)
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
}