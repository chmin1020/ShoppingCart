package com.FallTurtle.shoppingcart.repository

import com.FallTurtle.shoppingcart.model.Cart
import com.FallTurtle.shoppingcart.model.CartDao
import javax.inject.Inject

/**
 * 이 앱 아키텍쳐의 repository 객체.
 * 앱 구조가 간단하기 때문에 repository room Database 만 가져오고 있다.
 */
class RoomDataRepository @Inject constructor(private val cartDao: CartDao) {
    suspend fun getList(): List<Cart> {
        return cartDao.getCarts()
    }

    suspend fun insertData(cart: Cart) {
        cartDao.insert(cart)
    }

    suspend fun deleteData(cart: Cart) {
        cartDao.delete(cart)
    }
}
