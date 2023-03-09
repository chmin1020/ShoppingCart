package com.FallTurtle.shoppingcart.model

import androidx.room.*

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: Cart)

    @Delete
    suspend fun delete(cart: Cart)

    @Query("select * from Cart")
    suspend fun getCarts(): List<Cart>
}