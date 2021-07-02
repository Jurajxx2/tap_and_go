package com.example.tapandgo.api

import com.example.tapandgo.model.remote.user.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @GET("users")
    suspend fun getProfile(): GetProfileResponse

    @POST("users")
    suspend fun register(@Body body: PostRegistrationRequest): PostRegistrationResponse

    @POST("sessions")
    suspend fun login(@Body body: PostLoginRequest): PostLoginResponse

    @POST("sessions/refresh")
    suspend fun refreshToken(@Body body: PostRefreshTokenRequest): PostRefreshTokenResponse
}