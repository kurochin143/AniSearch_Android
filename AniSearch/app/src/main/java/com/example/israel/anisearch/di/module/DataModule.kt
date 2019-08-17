package com.example.israel.anisearch.di.module

import com.example.israel.anisearch.api.AniListApiService
import com.example.israel.anisearch.repository.AniSearchRepositoryImpl
import com.example.israel.anisearch.repository.AniSearchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideAniSearchRepository(aniListApiService: AniListApiService) : AniSearchRepository {
        return AniSearchRepositoryImpl(aniListApiService)
    }

}