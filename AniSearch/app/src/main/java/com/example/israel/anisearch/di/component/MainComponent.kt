package com.example.israel.anisearch.di.component;

import com.example.israel.anisearch.di.module.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
@Singleton
interface MainComponent {

    fun plus(topModule: TopModule) : TopComponent
    fun plus(searchModule: SearchModule) : SearchComponent
    fun plus(animeDetailsModule: AnimeDetailsModule) : AnimeDetailsComponent

}
