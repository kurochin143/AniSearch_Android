package com.example.israel.anisearch.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.israel.anisearch.repository.AniSearchRepository
import com.example.israel.anisearch.view_model.TopViewModel

class TopVMFactory(private val aniSearchRepository: AniSearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TopViewModel(aniSearchRepository) as T
    }

}