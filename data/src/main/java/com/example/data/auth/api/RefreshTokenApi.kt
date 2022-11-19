package com.example.data.auth.api

import com.example.domain.auth.AuthRequest
import com.example.data.auth.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenApi {
    @POST("/refresh_token")
    suspend fun refreshToken(
        @Body authRequest: AuthRequest
    ): AuthResponse
}