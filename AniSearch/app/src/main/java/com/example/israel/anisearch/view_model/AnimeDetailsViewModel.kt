package com.example.israel.anisearch.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.israel.anisearch.anilist_api.Anime
import com.example.israel.anisearch.repository.AniSearchRepository
import io.reactivex.disposables.Disposable

class AnimeDetailsViewModel(private val aniSearchRepository: AniSearchRepository) : ViewModel() {

    private var getAnimeDetailsDisposable: Disposable? = null
    private var animeDetailsLiveData = MutableLiveData<Anime?>()

    fun getAnimeDetailsLiveData(): LiveData<Anime?> = animeDetailsLiveData

    fun getAnimeDetails(id: Int) {
        getAnimeDetailsDisposable = aniSearchRepository.getAnimeDetails(id).subscribe(
            {
                animeDetailsLiveData.postValue(it?.data?.media)
            },
            {
                animeDetailsLiveData.postValue(null)
            })
    }

    override fun onCleared() {
        super.onCleared()

        getAnimeDetailsDisposable?.dispose()
    }
}