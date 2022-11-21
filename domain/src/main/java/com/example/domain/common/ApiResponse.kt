package com.example.domain.common

data class ApiResponse<T>(val successful: Boolean, val data: T? = null, val message: String? = null, val code: Int? = null)