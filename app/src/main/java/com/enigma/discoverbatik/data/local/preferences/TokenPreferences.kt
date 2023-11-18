package com.enigma.discoverbatik.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_preferences")

class TokenPreferences(private val dataStore: DataStore<Preferences>) {

    private val TOKEN_AUTH_KEY = stringPreferencesKey("token_auth")

//    Buat Save Token
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_AUTH_KEY] = token
        }
    }

//    Buat Get Token
    suspend fun getToken(): String {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_AUTH_KEY] ?: ""
    }

//    Buat Clear Token (bisa buat logout)
    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_AUTH_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TokenPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): TokenPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = TokenPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }


}