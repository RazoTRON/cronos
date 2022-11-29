package com.example.domain.auth.use_case

import com.example.domain.auth.AuthRepository
import com.example.domain.auth.request.AuthRequest
import com.example.domain.auth.request.AuthResponse
import com.example.domain.common.ApiResponse
import com.example.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SignInUseCase(private val repository: AuthRepository) {
    suspend fun invoke(authRequest: AuthRequest): Flow<Resource<ApiResponse<AuthResponse>>> = flow {
        lateinit var response: ApiResponse<AuthResponse>
        try {
            emit(Resource.Loading())
            response = repository.signIn(authRequest)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> emit(
                    Resource.Error(
                        code = 401,
                        message = "You are not authorized."
                    )
                )
                409 -> emit(
                    Resource.Error(
                        code = 409,
                        message = "Invalid login or password."
                    )
                )
                else -> emit(
                    Resource.Error(
                        code = null,
                        e.localizedMessage ?: "An unexpected error occurred."
                    )
                )
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