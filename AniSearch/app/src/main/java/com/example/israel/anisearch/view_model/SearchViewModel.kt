package com.example.israel.anisearch.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.anilist_api.statics.MediaSearchSort
import com.example.israel.anisearch.model.SearchResult
import com.example.israel.anisearch.model.SearchResults
import com.example.israel.anisearch.repository.AniSearchRepository
import io.reactivex.disposables.Disposable

class SearchViewModel(private val aniSearchRepository: AniSearchRepository) : ViewModel() {

    private var searchDisposable: Disposable? = null
    private val searchResultsLiveData = MutableLiveData<SearchResults>()

    fun getSearchResultsLiveData(): LiveData<SearchResults> = searchResultsLiveData

    fun searchAnime(page: Int, perPage: Int, search: String, sort: String = MediaSearchSort.SEARCH_MATCH) {
        searchDisposable = aniSearchRepository.searchAnime(page, perPage, search, sort)
            .map {animeSearchResult ->
                val media = animeSearchResult.data?.page?.media ?: return@map null
                val pageInfo = animeSearchResult.data?.page?.pageInfo ?: return@map null
                val currentPage = pageInfo.currentPage ?: 0
                val lastPage = pageInfo.lastPage ?: 0

                val searchResults = ArrayList<SearchResult>(media.size)
                media.forEach {anime ->
                    searchResults.add(SearchResult(AniListType.ANIME, anime.id, anime.title?.english, anime.coverImage?.medium))
                }

                return@map SearchResults(
                    AniListType.ANIME,
                    searchResults,
                    currentPage,
                    lastPage
                )
            }
            .subscribe({
                searchResultsLiveData.postValue(it)
            }, {
                searchResultsLiveData.postValue(null)
            }
        )
    }

    fun searchManga(page: Int, perPage: Int, search: String, sort: String = MediaSearchSort.SEARCH_MATCH) {

    }

}