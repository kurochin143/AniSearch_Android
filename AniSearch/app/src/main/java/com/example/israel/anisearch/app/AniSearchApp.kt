package com.example.israel.anisearch.app

import android.app.Application
import com.example.israel.anisearch.di.component.DaggerMainComponent
import com.example.israel.anisearch.di.component.MainComponent
import com.example.israel.anisearch.di.module.AnimeDetailsModule
import com.example.israel.anisearch.di.module.SearchModule
import com.example.israel.anisearch.di.module.TopModule
import com.squareup.leakcanary.LeakCanary

class AniSearchApp : Application() {

    private lateinit var aniSearchMainComponent: MainComponent

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        aniSearchMainComponent = DaggerMainComponent.builder().build()
    }

    fun getTopComponent() = aniSearchMainComponent.plus(TopModule())

    fun getSearchComponent() = aniSearchMainComponent.plus(SearchModule())

    fun getAnimeDetailsComponent() = aniSearchMainComponent.plus(AnimeDetailsModule())

}