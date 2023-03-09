package com.FallTurtle.shoppingcart.di

import android.content.Context
import androidx.room.Room
import com.FallTurtle.shoppingcart.model.CartDao
import com.FallTurtle.shoppingcart.model.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDB {
        return Room.databaseBuilder(context, RoomDB::class.java, "RDB")
                    .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideBigListDao(roomDB: RoomDB): CartDao{
        return roomDB.bigListDao()
    }
}