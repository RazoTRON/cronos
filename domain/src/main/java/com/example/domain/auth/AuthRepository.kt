package com.example.domain.auth

interface AuthRepository {
    suspend fun signIn(authRequest: AuthRequest): AuthResponse
    suspend fun signUp(authRequest: AuthRequest): AuthResponse
    suspend fun authenticate()
}