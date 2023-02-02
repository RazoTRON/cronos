package com.example.data.di

import com.example.domain.auth.AuthRepository
import com.example.domain.auth.use_case.AuthenticateUseCase
import com.example.domain.auth.use_case.GetStatusUseCase
import com.example.domain.auth.use_case.SignInUseCase
import com.example.domain.auth.use_case.SignUpUseCase
import com.example.domain.search.CronosService
import com.example.domain.search.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideFindPeopleUseCase(repository: CronosService): FindPeoplesUseCase {
        return FindPeoplesUseCase(repository)
    }
    @Provides
    fun provideGetPeopleUseCase(repository: CronosService): GetPeopleUseCase {
        return GetPeopleUseCase(repository)
    }
    @Provides
    fun provideFindPassportUseCase(repository: CronosService): FindPassportUseCase {
        return FindPassportUseCase(repository)
    }

    @Provides
    fun provideFindAddressUseCase(repository: CronosService): FindAddressUseCase {
        return FindAddressUseCase(repository)
    }

    @Provides
    fun provideFindAnketaUseCase(repository: CronosService): FindAnketaUseCase {
        return FindAnketaUseCase(repository)
    }

    @Provides
    fun provideGetStatusUseCase(repository: AuthRepository): GetStatusUseCase {
        return GetStatusUseCase(repository)
    }
    @Provides
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }
    @Provides
    fun provideSignInUseCase(repository: AuthRepository): SignInUseCase {
        return SignInUseCase(repository)
    }
    @Provides
    fun provideAuthenticateUseCase(repository: AuthRepository): AuthenticateUseCase {
        return AuthenticateUseCase(repository)
    }
}