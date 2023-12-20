package com.enigma.discoverbatik.view.activity.cart

import com.enigma.discoverbatik.data.remote.response.BatikItem

class CartManager {

    private val cartItems = mutableListOf<BatikItem>()

    fun addItem(item: BatikItem) {
        val existingItem = cartItems.find { it.id == item.id }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(item.copy(quantity = 1))
        }
    }

    fun removeItem(item: BatikItem) {
        cartItems.removeIf { it.id == item.id }
    }

    fun getCartItems(): List<BatikItem> {
        return cartItems.toList()
    }

    fun updateQuantity(item: BatikItem, newQuantity: Int) {
        val existingItem = cartItems.find { it.id == item.id }

        if (existingItem != null) {
            existingItem.quantity = newQuantity
        }
    }

    fun calculateTotalPrice(): Pair<Int, Int> {
        var subTotal = 0
        val delivery = 6000
        val tax = 3000
        var total = 0

        for (item in cartItems) {
            subTotal += item.price * item.quantity
        }

        total = subTotal + delivery + tax

        return Pair(subTotal, total)
    }

    companion object {
        private var instance: CartManager? = null

        fun getInstance(): CartManager {
            if (instance == null) {
                instance = CartManager()
            }
            return instance!!
        }
    }

}