package com.enigma.discoverbatik.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItemCategoryResponse (
    val name: String
) : Parcelable