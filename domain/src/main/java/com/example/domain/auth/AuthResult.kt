package com.example.domain.auth

sealed class AuthResult<T>(val data: T? = null) {
    class Authorized<T>(data: T? = null) : AuthResult<T>(data = data)
    class Unauthorized<T> : AuthResult<T>()
    class Error<T>() : AuthResult<T>()
}
