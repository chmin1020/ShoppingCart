package com.example.shoppingcart.MVVM

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppingcart.item.bigList

@Dao
interface bigListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bigList: bigList)

    @Delete
    fun delete(bigList: bigList)

    @Query("select * from bigList")
    fun getAllBigList(): LiveData<List<bigList>>
}