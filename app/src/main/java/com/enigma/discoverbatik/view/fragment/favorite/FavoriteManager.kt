package com.enigma.discoverbatik.view.fragment.favorite

import com.enigma.discoverbatik.data.remote.response.FavoriteItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FavoriteManager private constructor() {

    private val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    private val favoriteReference: DatabaseReference
        get() {
            val userId = userId ?: throw IllegalStateException("User not authenticated")
            return FirebaseDatabase.getInstance().reference.child("favorites").child(userId)
        }

    fun addToFavorites(itemId: String, itemName: String, itemLocation: String, itemDescription: String) {
        val favoriteItem = FavoriteItem(itemId, itemName, itemLocation, itemDescription)
        favoriteReference.child(itemId).setValue(favoriteItem)
    }

    fun removeFromFavorites(itemId: String) {
        favoriteReference.child(itemId).removeValue()
    }

    fun getFavoriteItems(): DatabaseReference {
        return favoriteReference
    }

    companion object {
        @Volatile
        private var instance: FavoriteManager? = null

        fun getInstance(): FavoriteManager =
            instance ?: synchronized(this) {
                instance ?: FavoriteManager().also { instance = it }
            }
    }

}