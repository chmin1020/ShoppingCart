package com.FallTurtle.shoppingcart.etc

import android.util.Log
import androidx.room.TypeConverter
import com.FallTurtle.shoppingcart.model.ChooseItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

//arrayList <-> string을 위한 converter 정의
object Converters {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): List<ChooseItem> {
        val items = mutableListOf<ChooseItem>()

        value?.substring(1 until value.lastIndex)?.split(",")?.forEach{
            val checked = (it[1] == 'T')
            val name = it.substring(2 until it.lastIndex)
            items.add(ChooseItem(checked, name))
        }
        Log.d("뚜뚜데이지2", items.joinToString(" "))
        return items
    }

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<ChooseItem>): String {
        val strConvertList = mutableListOf<String>()

        list.forEach{
            strConvertList.add("${ if(it.checked) 'T' else 'F' }${it.name}")
        }


        Log.d("뚜뚜데이지", Gson().toJson(strConvertList).toString())
        return Gson().toJson(strConvertList)
    }
}
