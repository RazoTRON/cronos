package com.example.domain.auth.use_case

import com.example.domain.auth.AuthRepository
import com.example.domain.search.CronosService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStatusUseCase(val repository: AuthRepository) {
    fun invoke(): Flow<Boolean> = flow {
        try {
            emit(false)
            repository.status()
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }
}