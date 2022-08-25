package com.FallTurtle.shoppingcart.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.FallTurtle.shoppingcart.MVVM.RoomViewModel
import com.FallTurtle.shoppingcart.adapter.SmallListAdapter
import com.FallTurtle.shoppingcart.databinding.ActivityDataBinding
import com.FallTurtle.shoppingcart.item.BigList
import com.FallTurtle.shoppingcart.item.CustomDialog
import java.text.SimpleDateFormat
import java.util.*

/**
 * 내용을 추가하거나 수정하기 위한 페이지를 담당하는 액티비티.
 * 이 액티비티는 기존에 있는 내용을 수정하기 위해, 혹은 새로운 내용을 만들기 위해 실행된다.
 * 따라서 edit 여부를 판단하는 기능과 뷰에 작성된 내용을 저장하는 기능으로 이루어진다.
 */
class DataActivity : AppCompatActivity() {
    //---------------------------------------
    // 인스턴스 영역
    //

    //viewModel (뷰모델 팩토리, 뷰모델 )
    private val viewModelFactory by lazy{ ViewModelProvider.AndroidViewModelFactory(this.application) }
    private val viewModel by lazy{ ViewModelProvider(this, viewModelFactory).get(RoomViewModel::class.java) }

    //viewBinding
    private val binding by lazy{ ActivityDataBinding.inflate(layoutInflater) }

    //페이지 내부 쇼핑 아이템을 보여줄 리사이클러뷰의 어댑터
    private val smallListAdapter: SmallListAdapter = SmallListAdapter()


    //---------------------------------------
    // 변수 영역
    //

    //페이지가 기존 아이템의 수정을 위해 실행되었을 경우 사용될 변수들
    private var isEdit:Boolean = false
    private var id:Int = -1
    private var firstTitle:String = ""


    //---------------------------------------
    // 생명 주기 콜백 영역
    //

    /* onCreate()에서는 리사이클러뷰, 모델을 통한 뷰 갱신 및 이벤트 리스너 설정을 수행 */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰 바인딩으로 화면 설정
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

    /* onBackPressed()에는 뒤로 가기를 누른 경우에 판단 및 수행해야 하는 내용이 담김 */
    override fun onBackPressed() {
        //페이지 속 내용이 수정되었다면 dialog 띄워서 저장 여부를 확인하게 한다.
        if (smallListAdapter.isChanged || firstTitle != binding.etTitle.text.toString())
            showDialogForChangedContents()
        else
            finish()
    }


    //---------------------------------------
    // 내부 함수 영역
    //

    /* 아이템 추가 또는 저장을 위한 버튼에 이벤트 리스너를 추가하는 함수 */
    private fun initListeners(){
        //아이템 추가 버튼 클릭 시 이벤트 (작성한 이름을 가져와 저장하고 editText 초기화)
        binding.ibAdd.setOnClickListener {
            val tmp: String = binding.etItem.text.toString()
            if (tmp != "") {
                smallListAdapter.insert(tmp)
                binding.etItem.text.clear()
            }
        }

        //저장 버튼 클릭 시 이벤트 (페이지 내용 저장 과정 함수 실행)
        binding.btnSave.setOnClickListener {
            contentSaveProgress()
        }
    }

    /* 기존 big 아이템 수정을 위해 페이지를 킨 경우, 기존 내용을 불러오는 함수 */
    private fun editPageSetting(){
        //id를 가져온다. (가져오지 못한다면 오류로 간주)
        id = intent.getIntExtra("id",-1)
        if(id == -1) {
            Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show()
            finish()
        }

        //저장된 내용에서 제목을 가져와서 editText 수정하고 내용 임시 저장(추후 내용 수정 여부 파악을 위해)
        firstTitle = binding.etTitle.text.toString()
        binding.etTitle.setText(firstTitle)

        //저장된 내용에서 쇼핑 아이템 이름과 체크 여부 리스트를 가져와서 리사이클러뷰(어댑터)에 적용
        smallListAdapter.itemList = intent.getStringArrayListExtra("itemList")
        smallListAdapter.checkList= intent.getStringArrayListExtra("checkList")
    }

    /* 내용을 room 데이터베이스에 저장하기 위한 함수 */
    private fun contentSaveProgress(){
        //현재 시간(날짜)을 가져와서 yyyy/MM/dd HH:mm 형식으로 저장
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA)
        val currentDate = dateFormat.format(Date(System.currentTimeMillis()))

        //내용을 저장 (제목, 이름과 체크 여부를 담은 리스트, 수정 중일 경우 id)
        val bigItem = BigList(
            binding.etTitle.text.toString(), currentDate,
            smallListAdapter.itemList, smallListAdapter.checkList
        )
        if(isEdit)
            bigItem.setId(id)

        //뷰모델을 통해 DB에 저장
        viewModel.insert(bigItem)

        //저장을 알리는 토스트 메시지와 함께 페이지 종료
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    /* 저장 여부를 묻는 다이얼로그를 생성해서 화면에 보여주는 함수 */
    private fun showDialogForChangedContents(){
        //저장 여부를 묻는 다이얼로그 생성
        val dialog = CustomDialog(this, "저장하시겠습니까?")

        //'네' 응답에 대한 처리 과정
        dialog.setOnPositiveClickListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked() {
                contentSaveProgress()
            }
        })

        //'아니오' 응답에 대한 처리 과정
        dialog.setOnNegativeClickListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked() {
                Toast.makeText(this@DataActivity, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        //생성하고 설정한 다이얼로그 화면에 출력
        dialog.create()
    }
}