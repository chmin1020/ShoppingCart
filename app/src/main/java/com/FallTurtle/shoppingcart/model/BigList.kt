package com.FallTurtle.shoppingcart.model

import androidx.room.*
import java.util.*

@Entity
data class BigList(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "itemList") val list: ArrayList<String>?,
    @ColumnInfo(name = "checkList") val list2: ArrayList<String>?,
    var title: String?,
    var date: String?
)
