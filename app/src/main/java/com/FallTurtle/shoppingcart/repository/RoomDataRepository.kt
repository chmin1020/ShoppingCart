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
class RoomDataRepository(context: Context) {
    //room DB 관련 인스턴스 (DB, DAO, elements)
    private val database: RoomDB = RoomDB.getDB(context)!!
    private val bigListDao: BigListDao = database.bigListDao()
    private val elements: LiveData<List<BigList>> = bigListDao.getAllBigList()

    fun getList(): LiveData<List<BigList>> {
        return elements
    }

    fun insertData(bigList: BigList) {
        try {
            val thread = Thread { bigListDao.insert(bigList) }
            thread.start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteData(bigList: BigList) {
        try {
            val thread = Thread { bigListDao.delete(bigList) }
            thread.start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
