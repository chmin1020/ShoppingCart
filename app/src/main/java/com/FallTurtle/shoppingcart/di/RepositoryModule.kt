package com.FallTurtle.shoppingcart.di

import android.content.Context
import com.FallTurtle.shoppingcart.repository.RoomDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideRoomDataRepository(@ApplicationContext context: Context) : RoomDataRepository{
        return RoomDataRepository(context)
    }
}