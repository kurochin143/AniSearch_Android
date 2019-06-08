package com.example.israel.anisearch.di.module

import com.example.israel.anisearch.repository.AniSearchRepository
import com.example.israel.anisearch.view_model.factory.AnimeDetailsVMFactory
import dagger.Module
import dagger.Provides

@Module
class AnimeDetailsModule {

    @Provides
    fun providesAnimeDetailsVMFactory(aniSearchRepository: AniSearchRepository): AnimeDetailsVMFactory {
        return AnimeDetailsVMFactory(aniSearchRepository)
    }

}