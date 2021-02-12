package com.FallTurtle.shoppingcart.item

import androidx.room.*
import java.util.*

@Entity //bigList 아이템을 room 데베에 저장하는 방식
class bigList(
    //리스트 제목, 최종 수정 날짜, 아이템 이름, 아이템 체크 여부
    @ColumnInfo(name = "title") private var title: String?, 
    @ColumnInfo(name = "date") private var date: String?, 
    @ColumnInfo(name = "itemList") private var list: ArrayList<String>?,
    @ColumnInfo(name = "checkList") private var list2: ArrayList<String>?
) {
    @PrimaryKey(autoGenerate = true)
    private var id = 0

    //프로그램 내에서 필요한 함수만 정의함
    fun getId(): Int {
        return id
    }
    fun setId(id: Int) {
        this.id = id
    }
    fun getTitle(): String? {
        return title
    }
    fun getDate(): String? {
        return date
    }
    fun getList(): ArrayList<String>? {
        return list
    }
    fun getList2(): ArrayList<String>? {
        return list2
    }
}
