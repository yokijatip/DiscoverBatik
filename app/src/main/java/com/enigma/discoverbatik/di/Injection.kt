package com.enigma.discoverbatik.di

import android.content.Context
import com.enigma.discoverbatik.data.remote.config.ApiConfig
import com.enigma.discoverbatik.repository.Repository

object Injection {

    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }

}