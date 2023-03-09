package com.FallTurtle.shoppingcart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChooseItem (
    val checked: Boolean,
    val name: String
): Parcelable