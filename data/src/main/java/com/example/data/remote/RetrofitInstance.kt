package com.example.data.remote

import android.content.SharedPreferences
import com.example.data.util.ACCESS_TOKEN
import com.example.data.util.BASE_URL
import com.example.data.util.REFRESH_TOKEN
import com.example.domain.common.ApiResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance(
    val headerInterceptor: HeaderInterceptor? = null,
    val tokenAuthenticator: TokenAuthenticator? = null,
) {

    private fun getClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.apply {
            if (headerInterceptor != null) addInterceptor(headerInterceptor)
            if (tokenAuthenticator != null) authenticator(tokenAuthenticator)
        }
        return client.build()
    }

    val gson = GsonBuilder()
        .setLenient()
        .create()

    val api: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}

class HeaderInterceptor(private val pref: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = pref.getAccessToken()
        val request = chain.request()
        val response = chain.proceed(
            request.newBuilder()
                .apply {
                    if (request.header("Authorization") == null) {
                        addHeader("Authorization", "Bearer $accessToken")
                    }
                }
                .method(request.method, request.body)
                .build()
        )
        return response
    }
}

fun SharedPreferences.getAccessToken() = this.getString(ACCESS_TOKEN, null)
fun SharedPreferences.getRefreshToken() = this.getString(REFRESH_TOKEN, null)
fun SharedPreferences.saveToken(accessToken: String? = null, refreshToken: String? = null) =
    this.edit().apply {
        accessToken?.let { putString(ACCESS_TOKEN, it) }
        refreshToken?.let { putString(REFRESH_TOKEN, it) }
    }.apply()