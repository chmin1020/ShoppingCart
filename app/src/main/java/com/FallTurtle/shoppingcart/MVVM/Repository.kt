package com.FallTurtle.shoppingcart.MVVM

import android.content.Context
import androidx.lifecycle.LiveData
import com.FallTurtle.shoppingcart.item.BigList

class Repository internal constructor(application: Context) {
    private val database: RoomDB = RoomDB.getDB(application)!!
    private val bigListDao: BigListDao = database.bigListDao()
    private val elements: LiveData<List<BigList>> = bigListDao.getAllBigList()

    //view모델에서 db에 접근을 요청하면 실행될 함수
    fun getAllBigList(): LiveData<List<BigList>> {
        return elements
    }
    
    //기본적인 삽입 이벤트 정의
    fun insert(bigList: BigList) {
        try {
            val thread = Thread { bigListDao.insert(bigList) }
            thread.start()
        } catch (e: Exception) {
        }
    }

    //기본적인 삭제 이벤트 정의
    fun delete(bigList: BigList) {
        try {
            val thread = Thread { bigListDao.delete(bigList) }
            thread.start()
        }
        catch (e: Exception) {
        }
    }
}
