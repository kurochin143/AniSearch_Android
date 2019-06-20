package com.example.israel.anisearch.di.component

import com.example.israel.anisearch.activity.TopListActivity
import com.example.israel.anisearch.di.module.TopModule
import com.example.israel.anisearch.fragment.TopListFragment
import dagger.Subcomponent

@Subcomponent(modules = [TopModule::class])
interface TopComponent {
    fun inject(topListFragment: TopListFragment)
}