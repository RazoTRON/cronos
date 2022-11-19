package com.example.data.auth.api

import com.example.domain.auth.AuthRequest
import com.example.data.auth.AuthResponse
import com.example.data.common.CronosResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface NoAuthApi {
    @POST("/signup")
    suspend fun signUp(
        @Body request: AuthRequest
    ): CronosResponse<AuthResponse>
}

