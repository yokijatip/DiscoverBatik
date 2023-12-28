package com.enigma.discoverbatik.di

import android.content.Context
import com.enigma.discoverbatik.data.remote.config.ApiConfig
import com.enigma.discoverbatik.data.remote.config.ApiConfigModel
import com.enigma.discoverbatik.data.remote.service.ApiService
import com.enigma.discoverbatik.repository.Repository

object Injection {

    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }

    fun provideRepositoryModel(context: Context): Repository {
        val apiService = ApiConfigModel.getApiService()
        return Repository(apiService)
    }

    fun proviceApiService(): ApiService {
        return ApiConfig.getApiService()
    }

}