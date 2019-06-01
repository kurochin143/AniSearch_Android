package com.example.israel.anisearch.di.module

import com.example.israel.anisearch.repository.AniSearchRepository
import com.example.israel.anisearch.view_model.factory.SearchVMFactory
import dagger.Module
import dagger.Provides

@Module
class SearchModule {
    @Provides
    fun provideSearchVMFactory(aniSearchRepository: AniSearchRepository) : SearchVMFactory {
        return SearchVMFactory(aniSearchRepository)
    }

}