package com.example.israel.anisearch.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.israel.anisearch.anilist_api.AniListType
import com.example.israel.anisearch.anilist_api.MediaSearchSort
import com.example.israel.anisearch.model.SearchResult
import com.example.israel.anisearch.model.SearchResults
import com.example.israel.anisearch.model.Top
import com.example.israel.anisearch.model.TopList
import com.example.israel.anisearch.repository.AniSearchRepository
import io.reactivex.disposables.Disposable

class SearchViewModel(private val aniSearchRepository: AniSearchRepository) : ViewModel() {

    private var searchDisposable: Disposable? = null
    private val searchResultsLiveData = MutableLiveData<SearchResults>()

    fun getSearchResultsLiveData(): LiveData<SearchResults> = searchResultsLiveData

    fun searchAnime(page: Int, perPage: Int, search: String, searchSort: MediaSearchSort = MediaSearchSort.SEARCH_MATCH) {
        searchDisposable = aniSearchRepository.searchAnime(page, perPage, search, searchSort)
            .map {animeSearchResult ->
                val media = animeSearchResult.data?.page?.media ?: return@map null

                val searchResults = ArrayList<SearchResult>(media.size)
                media.forEach {anime ->
                    searchResults.add(SearchResult(AniListType.ANIME, anime.id, anime.title?.english, anime.coverImage?.medium))
                }

                return@map SearchResults(
                    AniListType.ANIME,
                    searchResults,
                    animeSearchResult.data!!.page!!.pageInfo!!.currentPage!!,
                    animeSearchResult.data!!.page!!.pageInfo!!.lastPage!!
                )
            }
            .subscribe({
                searchResultsLiveData.postValue(it)
            }, {
                searchResultsLiveData.postValue(null)
            }
        )
    }

}