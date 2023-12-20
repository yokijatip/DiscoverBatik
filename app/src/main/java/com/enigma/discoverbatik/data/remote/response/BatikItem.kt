package com.enigma.discoverbatik.data.remote.response

data class BatikItem (
    val id: String,
    val name: String,
    var price: Int,
    var quantity: Int
)