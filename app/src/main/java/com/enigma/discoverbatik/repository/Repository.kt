package com.enigma.discoverbatik.repository

import com.enigma.discoverbatik.data.remote.response.DetailResponse
import com.enigma.discoverbatik.data.remote.response.LoginResponse
import com.enigma.discoverbatik.data.remote.response.PopularBatikResponse
import com.enigma.discoverbatik.data.remote.response.RegisterResponse
import com.enigma.discoverbatik.data.remote.service.ApiService
import retrofit2.Response
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
    suspend fun getAllBatik(): Response<List<PopularBatikResponse>> {
        return apiService.getAllBatik()
    }

    suspend fun getDetailById(id: Int): DetailResponse {
         return apiService.getDetailById(id)
    }


}