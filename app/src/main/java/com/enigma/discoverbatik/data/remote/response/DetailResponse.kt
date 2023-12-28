package com.enigma.discoverbatik.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("batik_name")
    val batikName: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("technique")
    val technique: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("asal_daerah")
    val asalDaerah: String? = null,

    var quantity : Int

)
