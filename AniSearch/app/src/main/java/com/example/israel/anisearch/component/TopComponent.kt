package com.example.israel.anisearch.component

import com.example.israel.anisearch.activity.HomeActivity
import com.example.israel.anisearch.component.scope.TopScope
import com.example.israel.anisearch.module.TopModule
import dagger.Subcomponent

@Subcomponent(modules = [TopModule::class])
interface TopComponent {
    fun inject(topActivity: HomeActivity)
}