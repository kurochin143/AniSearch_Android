package com.example.israel.anisearch.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.israel.anisearch.anilist_api.AniListType
import com.example.israel.anisearch.model.Top
import com.example.israel.anisearch.model.TopResult
import com.example.israel.anisearch.repository.AniSearchRepository
import io.reactivex.disposables.CompositeDisposable

class TopViewModel(private val aniSearchRepository: AniSearchRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val topAnimeLiveData = MutableLiveData<TopResult>()

    fun getTopAnimeLiveData(): LiveData<TopResult> {
        return topAnimeLiveData
    }

    fun getTopAnime(page: Int, perPage: Int) {
        compositeDisposable.add(aniSearchRepository.getTopAnime(page, perPage)
            .map {animeSearchResult ->
                val media = animeSearchResult.data?.page?.media ?: return@map null

                val topList = ArrayList<Top>(media.size)
                media.forEach {anime ->
                    topList.add(Top(AniListType.ANIME, anime.title?.english, anime.coverImage?.medium))
                }

                return@map TopResult(AniListType.ANIME, topList)
            }
            .subscribe({
                topAnimeLiveData.postValue(it)
            }, {
                // TODO return string of error
                topAnimeLiveData.postValue(null)
            }))
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}