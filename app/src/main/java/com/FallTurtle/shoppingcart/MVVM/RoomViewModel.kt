package com.FallTurtle.shoppingcart.MVVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.FallTurtle.shoppingcart.item.BigList

class RoomViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(application.applicationContext)
    private val elements: LiveData<List<BigList>> = repository.getAllBigList()

    //쓰레드에서 접근할 메소드 생성
    fun getAllBigList() : LiveData<List<BigList>> {
        return elements
    }
    //기본적인 삽입 이벤트 정의
    fun insert(bigList: BigList) {
        repository.insert(bigList)
    }
    //기본적인 삭제 이벤트 정의
    fun delete(bigList: BigList) {
        repository.delete(bigList)
    }

}
