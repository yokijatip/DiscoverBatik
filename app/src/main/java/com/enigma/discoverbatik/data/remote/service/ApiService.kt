package com.enigma.discoverbatik.data.remote.service

import com.enigma.discoverbatik.data.remote.response.DetailResponse
import com.enigma.discoverbatik.data.remote.response.LoginResponse
import com.enigma.discoverbatik.data.remote.response.PopularBatikResponse
import com.enigma.discoverbatik.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("allbatik")
    suspend fun getAllBatik(): Response<List<PopularBatikResponse>>

    @GET("batik/{id}")
    suspend fun getDetailById(
        @Path("id") id: Int,
    ): DetailResponse

}