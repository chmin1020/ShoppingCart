package com.FallTurtle.shoppingcart.di

import com.FallTurtle.shoppingcart.model.BigListDao
import com.FallTurtle.shoppingcart.repository.RoomDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideRoomDataRepository(bigListDao: BigListDao) : RoomDataRepository{
        return RoomDataRepository(bigListDao)
    }
}