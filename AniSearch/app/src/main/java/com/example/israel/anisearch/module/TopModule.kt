package com.example.israel.anisearch.module

import com.example.israel.anisearch.repository.AniSearchRepository
import com.example.israel.anisearch.view_model.factory.TopVMFactory
import dagger.Module
import dagger.Provides

@Module
class TopModule {

    @Provides
    fun provideTopVMFactory(aniSearchRepository: AniSearchRepository) : TopVMFactory {
        return TopVMFactory(aniSearchRepository)
    }

}