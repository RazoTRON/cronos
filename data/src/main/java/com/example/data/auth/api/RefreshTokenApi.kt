package com.example.data.auth.api

import com.example.domain.auth.AuthRequest
import com.example.domain.auth.AuthResponse
import com.example.domain.auth.TokenRequest
import com.example.domain.common.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenApi {
    @POST("/refresh_token")
    suspend fun refreshToken(
        @Body token: TokenRequest
    ): ApiResponse<AuthResponse>
}