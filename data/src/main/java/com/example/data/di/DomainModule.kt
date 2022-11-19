package com.example.data.di

import com.example.domain.search.repository.CronosRepository
import com.example.domain.search.use_case.*
import com.example.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideFindPeopleUseCase(repository: CronosRepository): FindPeoplesUseCase {
        return FindPeoplesUseCase(repository)
    }
    @Provides
    fun provideFindPassportUseCase(repository: CronosRepository): FindPassportUseCase {
        return FindPassportUseCase(repository)
    }

    @Provides
    fun provideFindAddressUseCase(repository: CronosRepository): FindAddressUseCase {
        return FindAddressUseCase(repository)
    }

    @Provides
    fun provideFindAnketaUseCase(repository: CronosRepository): FindAnketaUseCase {
        return FindAnketaUseCase(repository)
    }
    @Provides
    fun provideFindPhoneUseCase(repository: CronosRepository): FindPhoneUseCase {
        return FindPhoneUseCase(repository)
    }

    @Provides
    fun provideGetStatusUseCase(repository: CronosRepository): GetStatusUseCase {
        return GetStatusUseCase(repository)
    }
}