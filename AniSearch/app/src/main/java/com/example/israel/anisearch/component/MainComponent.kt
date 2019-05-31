package com.example.israel.anisearch.component;

import com.example.israel.anisearch.module.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
@Singleton
interface MainComponent {

    fun plus(topModule: TopModule) : TopComponent

}
