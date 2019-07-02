package com.example.israel.anisearch.di.component

import com.example.israel.anisearch.di.module.AnimeDetailsModule
import com.example.israel.anisearch.fragment.AnimeDetailsFragment
import com.example.israel.anisearch.fragment.MediaDetailsCharactersFragment
import dagger.Subcomponent

@Subcomponent(modules = [AnimeDetailsModule::class])
interface AnimeDetailsComponent {
    fun inject(animeDetailsFragment: AnimeDetailsFragment)
    fun inject(mediaDetailsCharactersFragment: MediaDetailsCharactersFragment)
}