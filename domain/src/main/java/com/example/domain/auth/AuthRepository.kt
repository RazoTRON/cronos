package com.example.domain.auth

interface AuthRepository {
    suspend fun signIn(authRequest: AuthRequest): AuthResult<Unit>
    suspend fun signUp(authRequest: AuthRequest): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}