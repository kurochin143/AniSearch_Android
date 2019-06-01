package com.example.israel.anisearch.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.israel.anisearch.anilist_api.AniListType
import com.example.israel.anisearch.model.Top
import com.example.israel.anisearch.model.TopList
import com.example.israel.anisearch.repository.AniSearchRepository
import io.reactivex.disposables.Disposable

class TopViewModel(private val aniSearchRepository: AniSearchRepository) : ViewModel() {

    private var getTopListDisposable: Disposable? = null
    private val topListLiveData = MutableLiveData<TopList>()

    fun getTopListLiveData(): LiveData<TopList> {
        return topListLiveData
    }

    fun getTopAnime(page: Int, perPage: Int) {
        getTopListDisposable?.dispose()

        getTopListDisposable = aniSearchRepository.getTopAnime(page, perPage)
            .map {animeSearchResult ->
                val media = animeSearchResult.data?.page?.media ?: return@map null

                val topList = ArrayList<Top>(media.size)
                media.forEach {anime ->
                    topList.add(Top(AniListType.ANIME, anime.id, anime.title?.english, anime.coverImage?.medium))
                }

                return@map TopList(AniListType.ANIME, topList)
            }
            .subscribe({
                topListLiveData.postValue(it)
            }, {
                // TODO return string of error
                topListLiveData.postValue(null)
            })
    }

    fun getTopManga(page: Int, perPage: Int) {
        getTopListDisposable?.dispose()

        getTopListDisposable = aniSearchRepository.getTopManga(page, perPage)
            .map {mangaSearchResult ->
                val media = mangaSearchResult.data?.page?.media ?: return@map null

                val topList = ArrayList<Top>(media.size)
                media.forEach {manga ->
                    topList.add(Top(AniListType.MANGA, manga.id, manga.title?.english, manga.coverImage?.medium))
                }

                return@map TopList(AniListType.MANGA, topList)
            }
            .subscribe({
                topListLiveData.postValue(it)
            }, {
                // TODO return string of error
                topListLiveData.postValue(null)
            })
    }

    fun getTopCharacters(page: Int, perPage: Int) {
        getTopListDisposable?.dispose()

        getTopListDisposable = aniSearchRepository.getTopCharacter(page, perPage)
            .map {characterSearchResult ->
                val media = characterSearchResult.data?.page?.characters ?: return@map null

                val topList = ArrayList<Top>(media.size)
                media.forEach {character ->
                    topList.add(Top(AniListType.CHARACTER, character.id, character.name?.getFullName(), character.image?.medium))
                }

                return@map TopList(AniListType.CHARACTER, topList)
            }
            .subscribe({
                topListLiveData.postValue(it)
            }, {
                // TODO return string of error
                topListLiveData.postValue(null)
            })
    }

    override fun onCleared() {
        super.onCleared()

        getTopListDisposable?.dispose()
    }
}