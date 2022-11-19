package com.example.data.auth.api

import com.example.domain.auth.AuthRequest
import com.example.data.auth.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("/signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): AuthResponse

    @GET("/authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )

}

