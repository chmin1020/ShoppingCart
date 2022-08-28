package com.FallTurtle.shoppingcart.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.FallTurtle.shoppingcart.model.BigList
import com.FallTurtle.shoppingcart.model.BigListDao
import com.FallTurtle.shoppingcart.model.RoomDB

/**
 * 이 앱 아키텍쳐의 repository 객체.
 * 앱 구조가 간단하기 때문에 repository room Database 만 가져오고 있다.
 */
class Repository internal constructor(application: Context) {
    //room DB 관련 인스턴스 (DB, DAO, elements)
    private val database: RoomDB = RoomDB.getDB(application)!!
    private val bigListDao: BigListDao = database.bigListDao()
    private val elements: LiveData<List<BigList>> = bigListDao.getAllBigList()

    //뷰모델에서 db에 접근을 요청하면 실행될 함수
    fun roomGetList(): LiveData<List<BigList>> {
        return elements
    }
    
    //기본적인 삽입 이벤트 정의
    fun roomInsert(bigList: BigList) {
        try {
            val thread = Thread { bigListDao.insert(bigList) }
            thread.start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //기본적인 삭제 이벤트 정의
    fun roomDelete(bigList: BigList) {
        try {
            val thread = Thread { bigListDao.delete(bigList) }
            thread.start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
