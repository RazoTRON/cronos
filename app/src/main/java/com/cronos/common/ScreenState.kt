package com.cronos.common

import com.example.domain.common.Resource

data class ScreenState(
    val isLoading: Boolean = false,
    val error: Resource.Error<Unit>? = null,
)