package com.example.shoppingcart.item

import androidx.room.*
import java.util.*

@Entity
class bigList(
    @ColumnInfo(name = "title")
    private var title: String?, @ColumnInfo(name = "date")
    private var date: String?, @ColumnInfo(name = "itemList")
    private var list: ArrayList<String>?, @ColumnInfo(name = "checkList")
    private var list2: ArrayList<String>?
) {
    @PrimaryKey(autoGenerate = true)
    private var id = 0


    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getList(): ArrayList<String>? {
        return list
    }

    fun setList(list: ArrayList<String>?) {
        this.list = list
    }

    fun getList2(): ArrayList<String>? {
        return list2
    }

    fun setList2(list2: ArrayList<String>?) {
        this.list2 = list2
    }
}
