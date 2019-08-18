package com.example.israel.anisearch.view_model

import androidx.lifecycle.MutableLiveData
import com.example.israel.anisearch.statics.AniListType
import com.example.israel.anisearch.statics.CharacterSearchSort
import com.example.israel.anisearch.statics.MediaSearchSort
import com.example.israel.anisearch.statics.StaffSearchSort
import com.example.israel.anisearch.repository.AniSearchRepository
import com.example.israel.anisearch.view_state.TopListViewState
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
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
                topListViewState.postValue(TopListViewState(isLoading = false))
                errorLiveData.postValue(it)
            }))
    }

    fun topMangaListSelected(page: Int, perPage: Int) {
        topListViewState.value = TopListViewState(isLoading = true)
        addDisposable(aniSearchRepository.searchManga(page, perPage, null, MediaSearchSort.SCORE_DESC)
            .subscribe({
                topListViewState.postValue(TopListViewState(
                    isLoading = false,
                    aniListType = AniListType.MANGA,
                    topList = it.data?.page?.media as MutableList<Any>? ?: throw Exception("Failed to load manga list")
                ))
            }, {
                topListViewState.postValue(TopListViewState(isLoading = false))
                errorLiveData.postValue(it)
            }))
    }

    fun topCharacterListSelected(page: Int, perPage: Int) {
        topListViewState.value = TopListViewState(isLoading = true)
        addDisposable(aniSearchRepository.searchCharacter(page, perPage, null, CharacterSearchSort.FAVOURITES_DESC)
            .subscribe({
                topListViewState.postValue(TopListViewState(
                    isLoading = false,
                    aniListType = AniListType.CHARACTER,
                    topList = it.data?.page?.characters as MutableList<Any>? ?: throw Exception("Failed to load character list")
                ))
            }, {
                topListViewState.postValue(TopListViewState(isLoading = false))
                errorLiveData.postValue(it)
            }))
    }

    fun topStaffListSelected(page: Int, perPage: Int) {
        topListViewState.value = TopListViewState(isLoading = true)
        addDisposable(aniSearchRepository.searchStaff(page, perPage, null, StaffSearchSort.FAVOURITES_DESC)
            .subscribe({
                topListViewState.postValue(TopListViewState(
                    isLoading = false,
                    aniListType = AniListType.STAFF,
                    topList = it.data?.page?.staffs as MutableList<Any>? ?: throw Exception("Failed to load staff list")
                ))
            }, {
                topListViewState.postValue(TopListViewState(isLoading = false))
                errorLiveData.postValue(it)
            }))
    }
}