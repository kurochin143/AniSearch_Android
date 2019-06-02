package com.example.israel.anisearch.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.anilist_api.statics.CharacterSearchSort
import com.example.israel.anisearch.anilist_api.statics.MediaSearchSort
import com.example.israel.anisearch.anilist_api.statics.StaffSearchSort
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

        getTopListDisposable = aniSearchRepository.searchAnime(page, perPage, null, MediaSearchSort.SCORE_DESC)
            .map {animeSearchResult ->
                val animeList = animeSearchResult.data?.page?.media ?: return@map null

                val topList = ArrayList<Top>(animeList.size)
                animeList.forEach { anime ->
                    topList.add(Top(AniListType.ANIME, anime.id, anime.title?.english, anime.coverImage?.medium))
                }

                return@map TopList(AniListType.ANIME, topList)
            }
            .subscribe({
                topListLiveData.postValue(it)
            }, {
                // TODO throwable live data
                topListLiveData.postValue(null)
            })
    }

    fun getTopManga(page: Int, perPage: Int) {
        getTopListDisposable?.dispose()

        getTopListDisposable = aniSearchRepository.searchManga(page, perPage, null, MediaSearchSort.SCORE_DESC)
            .map {mangaSearchResult ->
                val mangaList = mangaSearchResult.data?.page?.media ?: return@map null

                val topList = ArrayList<Top>(mangaList.size)
                mangaList.forEach { manga ->
                    topList.add(Top(AniListType.MANGA, manga.id, manga.title?.english, manga.coverImage?.medium))
                }

                return@map TopList(AniListType.MANGA, topList)
            }
            .subscribe({
                topListLiveData.postValue(it)
            }, {
                // TODO throwable live data
                topListLiveData.postValue(null)
            })
    }

    fun getTopCharacters(page: Int, perPage: Int) {
        getTopListDisposable?.dispose()

        getTopListDisposable = aniSearchRepository.searchCharacter(page, perPage, null, CharacterSearchSort.FAVOURITES_DESC)
            .map {characterSearchResult ->
                val characters = characterSearchResult.data?.page?.characters ?: return@map null

                val topList = ArrayList<Top>(characters.size)
                characters.forEach { character ->
                    topList.add(Top(AniListType.CHARACTER, character.id, character.name?.getFullName(), character.image?.medium))
                }

                return@map TopList(AniListType.CHARACTER, topList)
            }
            .subscribe({
                topListLiveData.postValue(it)
            }, {
                // TODO throwable live data
                topListLiveData.postValue(null)
            })
    }

    fun getTopStaffs(page: Int, perPage: Int) {
        getTopListDisposable?.dispose()

        getTopListDisposable = aniSearchRepository.searchStaff(page, perPage, null, StaffSearchSort.FAVOURITES_DESC)
            .map { staffSearchResult ->
                val staffs = staffSearchResult.data?.page?.staffs ?: return@map null

                val topList = ArrayList<Top>(staffs.size)
                staffs.forEach {staff ->
                    topList.add(Top(AniListType.STAFF, staff.id, staff.name?.getFullName(), staff.image?.medium))
                }

                return@map TopList(AniListType.STAFF, topList)
            }
            .subscribe({
                topListLiveData.postValue(it)
            }, {
                // TODO throwable live data
                topListLiveData.postValue(null)
            })
    }

    override fun onCleared() {
        super.onCleared()

        getTopListDisposable?.dispose()
    }
}