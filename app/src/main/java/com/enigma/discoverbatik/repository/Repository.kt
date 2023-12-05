package com.enigma.discoverbatik.repository

import com.enigma.discoverbatik.data.remote.response.DetailResponse
import com.enigma.discoverbatik.data.remote.response.LoginResponse
import com.enigma.discoverbatik.data.remote.response.PopularItemResponse
import com.enigma.discoverbatik.data.remote.response.RegisterResponse
import com.enigma.discoverbatik.data.remote.service.ApiService
import retrofit2.awaitResponse

class Repository(
    private val apiService: ApiService
) {

    //    Fungsi Buat Register
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return try {
            val response = apiService.postRegister(name, email, password).awaitResponse()
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw Exception("Gagal Membuat Akun")
            }
        } catch (e: Exception) {
            throw e
        }
    }


    //    Fungsi Buat Login
    suspend fun login(email: String, password: String): LoginResponse {
        return try {
            val response = apiService.postLogin(email, password).awaitResponse()
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw Exception("Gagal Login")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    //    Fungsi Buat Get Item Popular
    suspend fun getStory(): PopularItemResponse {
        return apiService.getStories()
    }

    suspend fun getDetailById(id: String): DetailResponse {
         try {
            val response = apiService.getDetailById(id)
             if (response.error == false) {
                 return response
             } else {
                 throw Exception("Gagal Mendapatkan Detail")
             }
        }catch (e: Exception) {
            throw e
        }
    }


}