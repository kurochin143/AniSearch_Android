package com.example.israel.anisearch.view_model

import androidx.lifecycle.MutableLiveData
import com.example.israel.anisearch.statics.AniListType
import com.example.israel.anisearch.statics.CharacterSearchSort
import com.example.israel.anisearch.statics.MediaSearchSort
import com.example.israel.anisearch.statics.StaffSearchSort
import com.example.israel.anisearch.model.presentation.Top
import com.example.israel.anisearch.model.presentation.TopList
import com.example.israel.anisearch.repository.AniSearchRepository
import com.example.israel.anisearch.view_state.TopListViewState
import java.lang.Exception

class TopViewModel(private val aniSearchRepository: AniSearchRepository) : BaseViewModel() {

    val errorLiveData by lazy { MutableLiveData<Throwable>() }
    val topListViewState by lazy { MutableLiveData<TopListViewState>() }

    fun topAnimeListSelected(page: Int, perPage: Int) {
        topListViewState.value = TopListViewState(isLoading = true)
        addDisposable(aniSearchRepository.searchAnime(page, perPage, null, MediaSearchSort.SCORE_DESC)
            .subscribe({
                topListViewState.postValue(TopListViewState(
                    isLoading = false,
                    aniListType = AniListType.ANIME,
                    topList = it.data?.page?.media as MutableList<Any>? ?: throw Exception("Failed to load anime list")
                ))
            }, {
                errorLiveData.postValue(it)
            }))
    }

    fun topMangaListSelected(page: Int, perPage: Int) {
        addDisposable(aniSearchRepository.searchManga(page, perPage, null, MediaSearchSort.SCORE_DESC)
            .subscribe({
                topListViewState.postValue(TopListViewState(
                    isLoading = true,
                    aniListType = AniListType.MANGA,
                    topList = it.data?.page?.media as MutableList<Any>? ?: throw Exception("Failed to load manga list")
                ))
            }, {
                errorLiveData.postValue(it)
            }))
    }

    fun topCharacterListSelected(page: Int, perPage: Int) {
        addDisposable(aniSearchRepository.searchCharacter(page, perPage, null, CharacterSearchSort.FAVOURITES_DESC)
            .subscribe({
                topListViewState.postValue(TopListViewState(
                    isLoading = true,
                    aniListType = AniListType.CHARACTER,
                    topList = it.data?.page?.characters as MutableList<Any>? ?: throw Exception("Failed to load character list")
                ))
            }, {
                errorLiveData.postValue(it)
            }))
    }

    fun topStaffListSelected(page: Int, perPage: Int) {
        addDisposable(aniSearchRepository.searchStaff(page, perPage, null, StaffSearchSort.FAVOURITES_DESC)
            .subscribe({
                topListViewState.postValue(TopListViewState(
                    isLoading = true,
                    aniListType = AniListType.STAFF,
                    topList = it.data?.page?.staffs as MutableList<Any>? ?: throw Exception("Failed to load staff list")
                ))
            }, {
                errorLiveData.postValue(it)
            }))
    }
}