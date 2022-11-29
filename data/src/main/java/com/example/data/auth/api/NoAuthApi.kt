package com.example.data.auth.api

import com.example.domain.auth.request.AuthRequest
import com.example.domain.common.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoAuthApi {
    @POST("/signup")
    suspend fun signUp(
        @Body request: AuthRequest
    ): ApiResponse<Unit>
    @GET("/status")
    suspend fun status(): Response<Unit>
}

