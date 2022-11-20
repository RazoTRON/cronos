package com.example.domain.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
