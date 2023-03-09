package com.FallTurtle.shoppingcart.repository

import com.FallTurtle.shoppingcart.model.BigList
import com.FallTurtle.shoppingcart.model.BigListDao
import javax.inject.Inject

/**
 * 이 앱 아키텍쳐의 repository 객체.
 * 앱 구조가 간단하기 때문에 repository room Database 만 가져오고 있다.
 */
class RoomDataRepository @Inject constructor(private val bigListDao: BigListDao) {
    suspend fun getList(): List<BigList> {
        return bigListDao.getAllBigList()
    }

    suspend fun insertData(bigList: BigList) {
        bigListDao.insert(bigList)
    }

    suspend fun deleteData(bigList: BigList) {
        bigListDao.delete(bigList)
    }
}
