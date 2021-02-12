package com.fallTurtle.shoppingcart.MVVM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fallTurtle.shoppingcart.item.bigList

@Database(entities = [bigList::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun bigListDao(): bigListDao //데이터베이스를 관리할 dao

    companion object {
        private var INSTANCE: RoomDB? = null
        fun getDB(context: Context): RoomDB? {
            if (INSTANCE == null) { //db가 없다면 만들자!
                synchronized(RoomDB::class) { //동기화로 불필요한 오류 방지
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDB::class.java, "RDB"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}