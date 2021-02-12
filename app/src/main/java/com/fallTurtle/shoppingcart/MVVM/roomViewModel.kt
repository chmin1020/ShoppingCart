package com.fallTurtle.shoppingcart.MVVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fallTurtle.shoppingcart.item.bigList

class roomViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(application.applicationContext)
    private val elements: LiveData<List<bigList>>

    //쓰레드에서 접근할 메소드 생성
    fun getAllBigList() : LiveData<List<bigList>> {
        return elements
    }
    //기본적인 삽입 이벤트 정의
    fun insert(bigList: bigList) {
        repository.insert(bigList)
    }
    //기본적인 삭제 이벤트 정의
    fun delete(bigList: bigList) {
        repository.delete(bigList)
    }

    //생성자(activity의 context를 받으면 액티비티 종료 시 메모리 누수 발생 가능, 따라서 어플리케이션 context 사용)
    init {
        elements = repository.getAllBigList()
    }
}
