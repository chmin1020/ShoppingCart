package com.FallTurtle.shoppingcart.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BigListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bigList: BigList)

    @Delete
    fun delete(bigList: BigList)

    @Query("select * from BigList")
    fun getAllBigList(): LiveData<List<BigList>>
}