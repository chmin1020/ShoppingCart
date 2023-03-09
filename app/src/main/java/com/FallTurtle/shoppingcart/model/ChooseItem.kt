package com.FallTurtle.shoppingcart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChooseItem (
    var checked: Boolean,
    val name: String
): Parcelable