package com.example.domain.auth.use_case

import com.example.domain.auth.AuthRepository
import com.example.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AuthenticateUseCase(private val repository: AuthRepository) {
    suspend fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.authenticate()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> emit(Resource.Error(
                    code = 401,
                    message = "You are not authorized."
                ))
                else -> emit(
                    Resource.Error(
                    code = null,
                    e.localizedMessage ?: "An unexpected error occurred."
                ))
            }

        } catch (e: IOException) {
            emit(
                Resource.Error(
                    code = null,
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        } catch (e: NullPointerException) {
            emit(Resource.Error(code = null, e.localizedMessage ?: "Please, specify your request."))
        }
    }
}