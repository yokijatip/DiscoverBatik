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

    fun calculateTotalPrice(): Int {
        var total = 0

        for (item in cartItems) {
            total += item.price * item.quantity
        }

        return total
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