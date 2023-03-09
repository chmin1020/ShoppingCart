package com.FallTurtle.shoppingcart.di

import com.FallTurtle.shoppingcart.model.CartDao
import com.FallTurtle.shoppingcart.repository.RoomDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideRoomDataRepository(cartDao: CartDao) : RoomDataRepository{
        return RoomDataRepository(cartDao)
    }
}