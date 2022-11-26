package com.example.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.data.auth.*
import com.example.data.auth.api.AuthApi
import com.example.data.auth.api.NoAuthApi
import com.example.data.auth.api.RefreshTokenApi
import com.example.data.remote.*
import com.example.data.repository.CronosRepositoryImpl
import com.example.domain.auth.AuthRepository
import com.example.domain.search.repository.CronosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideHeaderInterceptor(pref: SharedPreferences): HeaderInterceptor {
        return HeaderInterceptor(pref)
    }

    @Provides
    @Singleton
    @Named("NoAuthRetrofit")
    fun provideRetrofitNoAuth() : RetrofitInstance {
        return RetrofitInstance()
    }

    @Provides
    @Singleton
    @Named("RefreshAuthRetrofit")
    fun provideRetrofitRefreshAuth(
        headerInterceptor: HeaderInterceptor,
    ): RetrofitInstance {
        return RetrofitInstance(
//            headerInterceptor = headerInterceptor,
        )
    }

    @Provides
    @Singleton
    @Named("AuthRetrofit")
    fun provideAuthRetrofit(
        headerInterceptor: HeaderInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): RetrofitInstance {
        return RetrofitInstance(
            headerInterceptor = headerInterceptor,
            tokenAuthenticator = tokenAuthenticator
        )
    }

    @Provides
    @Singleton
    fun provideCronosApi(@Named("AuthRetrofit") retrofit: RetrofitInstance): CronosApi {
        return retrofit.api.create(CronosApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: CronosApi): CronosRepository {
        return CronosRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        val masterKeyAlias = MasterKey(context = app.applicationContext) // TODO

        return EncryptedSharedPreferences.create(
            app.applicationContext,
            "secret_shared_prefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, noAuthApi: NoAuthApi, pref: SharedPreferences, hashPasswordUseCase: HashPasswordUseCase): AuthRepository {
        return AuthRepositoryImpl(api, noAuthApi, pref, hashPasswordUseCase)
    }

    @Provides
    @Singleton
    fun provideHashPasswordUseCase(@ApplicationContext context: Context): HashPasswordUseCase {
        return HashPasswordUseCase(context)
    }

    @Provides
    @Singleton
    fun provideAuthApi(@Named("AuthRetrofit") retrofit: RetrofitInstance): AuthApi {
        return retrofit.api.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoAuthApi2(@Named("NoAuthRetrofit") retrofit: RetrofitInstance): NoAuthApi {
        return retrofit.api.create(NoAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoAuthApi(@Named("RefreshAuthRetrofit") retrofit: RetrofitInstance): RefreshTokenApi {
        return retrofit.api.create(RefreshTokenApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(pref: SharedPreferences): TokenAuthenticator {
        return TokenAuthenticator(pref)
    }
}