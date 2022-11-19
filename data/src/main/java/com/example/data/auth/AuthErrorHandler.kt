package com.example.data.auth

import android.util.Log
import com.example.domain.auth.AuthResult
import retrofit2.HttpException

suspend fun authErrorHandler(apiRequest: suspend () -> Unit): AuthResult<Unit> {
    return try {
        apiRequest.invoke()
        AuthResult.Authorized()
    } catch (e: HttpException) {
        Log.e("AUTH", "${e.code()}: ${e.message}")
        if (e.code() == 401) {
            AuthResult.Unauthorized()
        } else {
            AuthResult.Error()
        }
    } catch (e: Exception) {
        Log.e("AUTH", "${e.localizedMessage}\n${e.stackTrace.joinToString("\n")}")
        AuthResult.Error()
    }
}