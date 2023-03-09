package com.FallTurtle.shoppingcart.etc

import androidx.room.TypeConverter
import com.FallTurtle.shoppingcart.model.ChooseItem
import com.google.gson.Gson

//list <-> string을 위한 converter 정의
object Converters {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<ChooseItem> {
        val items = mutableListOf<ChooseItem>()
        if(value.length > 2) {
            value.substring(1 until value.lastIndex).split(",").forEach {
                val checked = (it[1] == 'T')
                val name = it.substring(2 until it.lastIndex)
                items.add(ChooseItem(checked, name))
            }
        }
        return items
    }

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<ChooseItem>): String {
        val strConvertList = mutableListOf<String>()

        list.forEach{
            strConvertList.add("${ if(it.checked) 'T' else 'F' }${it.name}")
        }
        return Gson().toJson(strConvertList)
    }
}
