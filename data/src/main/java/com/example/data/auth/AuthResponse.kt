package com.example.data.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
