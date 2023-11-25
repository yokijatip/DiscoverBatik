package com.enigma.discoverbatik.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularItemResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("listStory")
    val listStory: List<ListStoryItem>
) : Parcelable

@Parcelize
data class ListStoryItem(
    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,
    @field:SerializedName("createdAt")
    val createdAt: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("description")
    val description: String? = null,
    @field:SerializedName("lon")
    val lon: Double? = null,
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("lat")
    val lat: Double? = null
) : Parcelable