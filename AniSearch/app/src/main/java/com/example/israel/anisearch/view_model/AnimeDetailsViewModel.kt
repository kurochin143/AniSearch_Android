package com.example.israel.anisearch.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.israel.anisearch.model.data.Anime
import com.example.israel.anisearch.model.data.Media
import com.example.israel.anisearch.repository.AniSearchRepository
import io.reactivex.disposables.Disposable

class AnimeDetailsViewModel(private val aniSearchRepository: AniSearchRepository) : ViewModel() {

    private var getAnimeDetailsDisposable: Disposable? = null
    private var animeDetailsLiveData = MutableLiveData<Anime?>()
    private var mediaCharactersLiveData = MutableLiveData<Media?>()
    private var errorLiveData = MutableLiveData<Throwable>()

    fun getAnimeDetailsLiveData(): LiveData<Anime?> = animeDetailsLiveData
    fun getMediaCharactersLiveData(): LiveData<Media?> = mediaCharactersLiveData
    fun getErrorLiveData(): LiveData<Throwable> = errorLiveData

    fun getAnimeDetails(id: Int) {
        getAnimeDetailsDisposable = aniSearchRepository.getAnimeDetails(id).subscribe(
            {
                animeDetailsLiveData.postValue(it?.data?.media)
            },
            {
                errorLiveData.postValue(it)
            }
        )
    }

    fun getMediaCharacters(id: Int, page: Int, perPage: Int, sort: String) {
        getAnimeDetailsDisposable = aniSearchRepository.getMediaCharacters(id, page, perPage, sort).subscribe(
            {
                mediaCharactersLiveData.postValue(it?.data?.media)
            },
            {
                errorLiveData.postValue(it)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()

        getAnimeDetailsDisposable?.dispose()
    }
}