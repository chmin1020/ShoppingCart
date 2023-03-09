package com.FallTurtle.shoppingcart.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.FallTurtle.shoppingcart.repository.RoomDataRepository
import com.FallTurtle.shoppingcart.model.BigList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 이 앱 아키텍쳐의 viewModel 객체.
 * 앱 구조가 간단하기 때문에 repository 객체를 통해 room DB 만 연결된다.
 */
@HiltViewModel
class ShoppingViewModel
    @Inject constructor(private val repository: RoomDataRepository) : ViewModel() {

    /* 룸 DB 리스트 데이터에 접근할 때 사용되는 함수 */
    fun getAllBigList() : LiveData<List<BigList>> {
        return repository.getList()
    }

    /* 기본적인 룸 DB의 삽입 이벤트를 위한 함수 */
    fun insert(bigList: BigList) {
        repository.insertData(bigList)
    }

    /* 기본적인 룸 DB의 삭제 이벤트를 위한 함수 */
    fun delete(bigList: BigList) {
        repository.deleteData(bigList)
    }
}
