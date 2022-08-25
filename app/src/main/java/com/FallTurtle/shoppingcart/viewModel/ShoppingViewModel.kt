package com.FallTurtle.shoppingcart.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.FallTurtle.shoppingcart.repository.Repository
import com.FallTurtle.shoppingcart.model.BigList

class ShoppingViewModel(application: Application) : AndroidViewModel(application) {
    //모델 정보를 가져올 때 사용할 repository 인스턴스
    private val repository: Repository = Repository(application.applicationContext)

    /* 쓰레드에서 모델의 리스트 데이터에 접근할 때 사용되는 함수 */
    fun getAllBigList() : LiveData<List<BigList>> {
        return repository.getAllBigList()
    }

    /* 기본적인 삽입 이벤트를 위한 함수 */
    fun insert(bigList: BigList) {
        repository.insert(bigList)
    }

    /* 기본적인 삭제 이벤트를 위한 함수 */
    fun delete(bigList: BigList) {
        repository.delete(bigList)
    }
}
