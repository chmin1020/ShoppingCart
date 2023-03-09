package com.FallTurtle.shoppingcart.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Cart(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val items: List<ChooseItem>,
    var title: String,
    var date: String?
) : Parcelable
