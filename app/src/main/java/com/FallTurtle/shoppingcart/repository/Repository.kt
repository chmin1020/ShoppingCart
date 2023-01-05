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

    /* roomDB 내에서 리스트를 가져오는 작업을 정의한 함수 */
    fun roomGetList(): LiveData<List<BigList>> {
        return elements
    }
    
    /* roomDB에 대한 기본적인 삽입 이벤트를 정의한 함수 */
    fun roomInsert(bigList: BigList) {
        try {
            val thread = Thread { bigListDao.insert(bigList) }
            thread.start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /* roomDB에 대한 기본적인 삭제 이벤트를 정의한 함수 */
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
