package com.example.domain.auth

import com.example.domain.common.ApiResponse

interface AuthRepository {
    suspend fun signIn(authRequest: AuthRequest): ApiResponse<AuthResponse>
    suspend fun signUp(authRequest: AuthRequest): ApiResponse<Unit>
    suspend fun authenticate()
}
