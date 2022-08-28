package com.FallTurtle.shoppingcart.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.FallTurtle.shoppingcart.repository.Repository
import com.FallTurtle.shoppingcart.model.BigList

/**
 * 이 앱 아키텍쳐의 viewModel 객체.
 * 앱 구조가 간단하기 때문에 repository 객체를 통해 room DB 만 연결된다.
 */
class ShoppingViewModel(application: Application) : AndroidViewModel(application) {
    //모델 정보를 가져올 때 사용할 repository 인스턴스
    private val repository: Repository = Repository(application.applicationContext)

    /* 룸 DB 리스트 데이터에 접근할 때 사용되는 함수 */
    fun getAllBigList() : LiveData<List<BigList>> {
        return repository.roomGetList()
    }

    /* 기본적인 룸 DB의 삽입 이벤트를 위한 함수 */
    fun insert(bigList: BigList) {
        repository.roomInsert(bigList)
    }

    /* 기본적인 룸 DB의 삭제 이벤트를 위한 함수 */
    fun delete(bigList: BigList) {
        repository.roomDelete(bigList)
    }
}
