package com.FallTurtle.shoppingcart.repository

import androidx.lifecycle.LiveData
import com.FallTurtle.shoppingcart.model.BigList
import com.FallTurtle.shoppingcart.model.BigListDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

/**
 * 이 앱 아키텍쳐의 repository 객체.
 * 앱 구조가 간단하기 때문에 repository room Database 만 가져오고 있다.
 */
class RoomDataRepository @Inject constructor(private val bigListDao: BigListDao) {
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
