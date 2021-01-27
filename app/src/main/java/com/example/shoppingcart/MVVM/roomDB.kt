package com.example.shoppingcart.MVVM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shoppingcart.item.bigList

@Database(entities = [bigList::class], version = 1)
@TypeConverters(Converters::class)
abstract class roomDB : RoomDatabase() {
    abstract fun bigListDao(): bigListDao

    companion object {
        private var INSTANCE: roomDB? = null
        fun getDB(context: Context): roomDB? {
            if (INSTANCE == null) {
                synchronized(roomDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        roomDB::class.java, "RDB"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}