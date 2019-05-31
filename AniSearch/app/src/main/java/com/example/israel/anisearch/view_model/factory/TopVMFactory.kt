package com.example.israel.anisearch.view_model.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.israel.anisearch.repository.AniSearchRepository
import com.example.israel.anisearch.view_model.TopViewModel

class TopVMFactory(private val aniSearchRepository: AniSearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopViewModel(aniSearchRepository) as T
    }

}