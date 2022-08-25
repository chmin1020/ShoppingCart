package com.FallTurtle.shoppingcart.MVVM

import androidx.lifecycle.LiveData
import androidx.room.*
import com.FallTurtle.shoppingcart.item.BigList

@Dao
interface BigListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bigList: BigList)

    @Delete
    fun delete(bigList: BigList)

    @Query("select * from BigList")
    fun getAllBigList(): LiveData<List<BigList>>
}