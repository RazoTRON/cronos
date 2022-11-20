package com.example.data.auth

import android.content.SharedPreferences
import android.util.Log
import com.example.data.auth.api.AuthApi
import com.example.data.auth.api.NoAuthApi
import com.example.data.remote.getAccessToken
import com.example.data.util.ACCESS_TOKEN
import com.example.data.util.HASH_PASSWORD
import com.example.data.util.REFRESH_TOKEN
import com.example.data.util.USER_ID
import com.example.domain.auth.AuthRepository
import com.example.domain.auth.AuthRequest
import com.example.domain.auth.AuthResponse
import com.example.domain.auth.AuthResult
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.HttpException
import retrofit2.Response

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val noAuthApi: NoAuthApi,
    private val prefs: SharedPreferences,
    private val hashPasswordUseCase: HashPasswordUseCase,
) : AuthRepository {

    override suspend fun signUp(authRequest: AuthRequest): AuthResponse {
        val response = noAuthApi.signUp(
            authRequest.copy(password = hashPasswordUseCase.invoke(authRequest.password))
        )
        Log.e("AUTH", response.message)
        return signIn(authRequest)
    }

//    override suspend fun signUp(authRequest: AuthRequest): AuthResult<Unit> {
//        return authErrorHandler {
//            val response = noAuthApi.signUp(
//                authRequest.copy(password = hashPasswordUseCase.invoke(authRequest.password))
//            )
//            Log.e("AUTH", response.message)
//            signIn(authRequest)
//        }
//    }

    override suspend fun signIn(authRequest: AuthRequest): AuthResponse {
        val hashedPassword = hashPasswordUseCase.invoke(authRequest.password)
        val response = api.signIn(authRequest.copy(password = hashedPassword))

        prefs.edit().putString(ACCESS_TOKEN, response.accessToken) // TODO
            .putString(REFRESH_TOKEN, response.refreshToken) // TODO
            .putString(USER_ID, response.userId) // TODO
            .putString(HASH_PASSWORD, hashedPassword) // TODO
            .apply()
        return response
    }

    override suspend fun authenticate() {
        val token = prefs.getAccessToken() ?: throw HttpException(Response.error<Unit>(401, EMPTY_RESPONSE))
        api.authenticate(token = "Bearer $token")
    }
}

