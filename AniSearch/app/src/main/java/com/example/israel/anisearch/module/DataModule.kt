package com.example.israel.anisearch.module

import com.example.israel.anisearch.anilist_api.AniListApiDao
import com.example.israel.anisearch.anilist_api.ApiService
import com.example.israel.anisearch.repository.AniListRepository
import com.example.israel.anisearch.repository.AniSearchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideAniListApiDao(apiService: ApiService) : AniListApiDao {
        return AniListApiDao(apiService)
    }

    @Provides
    @Singleton
    fun provideAniSearchRepository(aniListApiDao: AniListApiDao) : AniSearchRepository {
        return AniListRepository(aniListApiDao)
    }

}