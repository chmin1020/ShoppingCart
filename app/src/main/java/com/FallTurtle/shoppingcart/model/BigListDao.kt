package com.FallTurtle.shoppingcart.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BigListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bigList: BigList)

    @Delete
    suspend fun delete(bigList: BigList)

    @Query("select * from BigList")
    suspend fun getAllBigList(): List<BigList>
}