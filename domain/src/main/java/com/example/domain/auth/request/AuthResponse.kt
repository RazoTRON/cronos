package com.example.domain.auth.request

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
