package com.example.israel.anisearch.app

import android.app.Application
import com.example.israel.anisearch.di.component.DaggerMainComponent
import com.example.israel.anisearch.di.component.MainComponent
import com.example.israel.anisearch.di.module.SearchModule
import com.example.israel.anisearch.di.module.TopModule

class AniSearchApp : Application() {

    private lateinit var aniSearchMainComponent: MainComponent

    override fun onCreate() {
        super.onCreate()

        aniSearchMainComponent = DaggerMainComponent.builder().build()
    }

    fun getTopComponent() = aniSearchMainComponent.plus(TopModule())

    fun getSearchComponent() = aniSearchMainComponent.plus(SearchModule())

}