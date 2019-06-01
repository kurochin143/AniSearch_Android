package com.example.israel.anisearch.di.component

import com.example.israel.anisearch.fragment.SearchResultsFragment
import com.example.israel.anisearch.di.module.SearchModule
import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    fun inject(searchResultsFragment: SearchResultsFragment)
}