package com.example.data.remote

import android.content.SharedPreferences
import com.example.data.GlobalNavigator
import com.example.domain.auth.AuthRequest
import com.example.data.auth.api.RefreshTokenApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okio.IOException
import retrofit2.HttpException

class TokenAuthenticator(
    private val pref: SharedPreferences,
) : Authenticator {
    // Called when server requests authorization, i.e. it returned HTTP 401. This happens if the current access token
    // has expired. In this case, refresh the token and send the original request with the fresh token.
    override fun authenticate(route: Route?, response: Response): Request? {
        val retrofit = RetrofitInstance(headerInterceptor = HeaderInterceptor(pref))

        return runBlocking {
            pref.getRefreshToken()?.let {
                pref.saveToken(accessToken = it)
                try {
                    retrofit.api.create(RefreshTokenApi::class.java)
                        .refreshToken(AuthRequest("", "")).let { authResponse -> // TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            pref.saveToken(authResponse.accessToken, authResponse.refreshToken)
                            response.request.newBuilder()
                                .addHeader("Authorization", "Bearer $it")
                                .build()
                        }
                } catch (e: HttpException) {
                    GlobalNavigator.logout()
                    null
                } catch (e: IOException) {
                    null
                }
            } ?: kotlin.run {
                GlobalNavigator.logout()
                null
            }
        }
    }
}