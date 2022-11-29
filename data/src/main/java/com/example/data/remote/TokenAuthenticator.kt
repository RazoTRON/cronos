package com.example.data.remote

import android.content.SharedPreferences
import android.util.Log
import com.example.domain.util.GlobalNavigator
import com.example.data.auth.api.RefreshTokenApi
import com.example.data.util.BASE_URL
import com.example.domain.auth.request.AuthResponse
import com.example.domain.auth.request.TokenRequest
import com.example.domain.common.ApiResponse
import kotlinx.coroutines.*
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenAuthenticator(
    private val pref: SharedPreferences,
) : Authenticator {
    // Called when server requests authorization, i.e. it returned HTTP 401. This happens if the current access token
    // has expired. In this case, refresh the token and send the original request with the fresh token.
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RefreshTokenApi::class.java)

    private var isExecuting = false
    private var logout = false
    override fun authenticate(route: Route?, response: Response): Request? {

        Log.e("TOKEN", "(logout: $logout, isEx: $isExecuting)")
        if (isExecuting) {
            runBlocking {
                while (isExecuting) {
                    Log.e("TOKEN", "Wait on end refresh token operation")
                    delay(100)
                }
            }
        } else {
            logout = false
            isExecuting = true


            Log.e("TOKEN", "Start refresh token operation")

            val newTokens = runBlocking {
                try {
                    if (!logout) {
                        refreshToken()
                    } else {
                        null
                    }
                } catch (e: HttpException) {
                    logout = true
                    GlobalNavigator.logout()
                    null
                } catch (e: Exception) {
                    null
                }
            }

            if (newTokens == null) {
                isExecuting = false
                return null
            }
            pref.saveToken(newTokens.data?.accessToken, newTokens.data?.refreshToken)
            Log.e("TOKEN", "Tokens saved")

            isExecuting = false
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${pref.getAccessToken()!!}")
            .build()
    }

    private suspend fun refreshToken(): ApiResponse<AuthResponse>? {
        val refreshToken = pref.getRefreshToken() ?: throw HttpException(retrofit2.Response.error<AuthResponse>(401, EMPTY_RESPONSE))
        return retrofit.refreshToken(TokenRequest(refreshToken))
    }
}
