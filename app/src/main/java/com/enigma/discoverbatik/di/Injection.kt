package com.enigma.discoverbatik.di

import android.content.Context
import com.enigma.discoverbatik.data.local.preferences.TokenPreferences
import com.enigma.discoverbatik.data.local.preferences.dataStore
import com.enigma.discoverbatik.data.remote.config.ApiConfig
import com.enigma.discoverbatik.repository.Repository
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRepository(context: Context): Repository {
        val dataStore = TokenPreferences.getInstance(context.dataStore)
        val user = runBlocking { dataStore.getToken() }
        val apiService = ApiConfig.getApiService(user)
        return Repository(apiService)
    }

}