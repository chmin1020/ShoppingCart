package com.example.shoppingcart.item

import androidx.room.*
import java.util.*

@Entity
class bigList {
    @PrimaryKey(autoGenerate = true)
    private var id = 0

    @ColumnInfo(name = "title")
    private var title: String? = null

    @ColumnInfo(name = "date")
    private var date: String? = null

    @ColumnInfo(name = "itemList")
    private var list: ArrayList<String>? = null

    @ColumnInfo(name = "checkList")
    private var list2: ArrayList<String>? = null


    constructor(
        title: String?,
        date: String?,
        list: ArrayList<String>?,
        list2: ArrayList<String>?
    ) {
        this.title = title
        this.date = date
        this.list = list
        this.list2 = list2
    }

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
