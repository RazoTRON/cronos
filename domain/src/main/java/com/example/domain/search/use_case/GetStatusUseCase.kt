package com.example.domain.search.use_case

import com.example.domain.search.repository.CronosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStatusUseCase(repository: CronosRepository) {
    fun invoke(repository: CronosRepository): Flow<Boolean> = flow {
        try {
            emit(false)
            repository.status()
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }
}