package com.FallTurtle.shoppingcart.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.FallTurtle.shoppingcart.etc.Converters

@Database(entities = [Cart::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun bigListDao(): CartDao //데이터베이스를 관리할 dao
}