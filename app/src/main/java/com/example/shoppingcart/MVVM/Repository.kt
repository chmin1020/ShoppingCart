package com.example.shoppingcart.MVVM

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.shoppingcart.item.bigList

class Repository internal constructor(application: Context) {
    private val database: roomDB
    private val bigListDao: bigListDao
    private val elements: LiveData<List<bigList?>?>?

    //view모델에서 db에 접근을 요청하면 실행될 함수
    fun getAllBigList(): LiveData<List<bigList?>?>? {
        return elements
    }

    fun insert(bigList: bigList?) {
        try {
            val thread = Thread { bigListDao.insert(bigList) }
            thread.start()
        } catch (e: Exception) {
        }
    }

    fun delete(bigList: bigList?) {
        try {
            val thread = Thread { bigListDao.delete(bigList) }
            thread.start()
        } catch (e: Exception) {
        }
    }

    //생성자 (application 받아옴)
    init {
        database = roomDB.getDB(application)
        bigListDao = database.bigListDao()
        elements = bigListDao.getAllBigList()
    }
}
