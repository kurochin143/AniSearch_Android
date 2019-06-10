package com.example.israel.anisearch.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.model.SearchResult
import com.example.israel.anisearch.model.SearchResults
import com.example.israel.anisearch.repository.AniSearchRepository
import io.reactivex.disposables.Disposable

class SearchViewModel(private val aniSearchRepository: AniSearchRepository) : ViewModel() {

    private var searchDisposable: Disposable? = null
    private val searchResultsLiveData = MutableLiveData<SearchResults>()

    fun getSearchResultsLiveData(): LiveData<SearchResults> = searchResultsLiveData

    fun searchAnime(page: Int, perPage: Int, search: String, sort: String) {
        searchDisposable = aniSearchRepository.searchAnime(page, perPage, search, sort)
            .map {animeSearchResult ->
                val animeList = animeSearchResult.data?.page?.media ?: return@map null
                val pageInfo = animeSearchResult.data?.page?.pageInfo ?: return@map null
                val currentPage = pageInfo.currentPage ?: 0
                val lastPage = pageInfo.lastPage ?: 0

                val searchResults = ArrayList<SearchResult>(animeList.size)
                animeList.forEach { anime ->
                    val searchResult = SearchResult.fromAnime(anime) ?: return@forEach

                    searchResults.add(searchResult)
                }
                searchResults.trimToSize()

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
                // TODO throwable live data
                searchResultsLiveData.postValue(null)
            }
        )
    }

    fun searchManga(page: Int, perPage: Int, search: String, sort: String) {
        searchDisposable = aniSearchRepository.searchManga(page, perPage, search, sort)
            .map {mangaSearchResult ->
                val mangaList = mangaSearchResult.data?.page?.media ?: return@map null
                val pageInfo = mangaSearchResult.data?.page?.pageInfo ?: return@map null
                val currentPage = pageInfo.currentPage ?: 0
                val lastPage = pageInfo.lastPage ?: 0

                val searchResults = ArrayList<SearchResult>(mangaList.size)
                mangaList.forEach { manga ->
                    val searchResult = SearchResult.fromManga(manga) ?: return@forEach

                    searchResults.add(searchResult)
                }
                searchResults.trimToSize()

                return@map SearchResults(
                    AniListType.MANGA,
                    searchResults,
                    currentPage,
                    lastPage
                )
            }
            .subscribe({
                searchResultsLiveData.postValue(it)
            }, {
                // TODO throwable live data
                searchResultsLiveData.postValue(null)
            })
    }

    fun searchCharacter(page: Int, perPage: Int, search: String, sort: String) {
        searchDisposable = aniSearchRepository.searchCharacter(page, perPage, search, sort)
            .map {characterSearchResult ->
                val characters = characterSearchResult.data?.page?.characters ?: return@map null
                val pageInfo = characterSearchResult.data?.page?.pageInfo ?: return@map null
                val currentPage = pageInfo.currentPage ?: 0
                val lastPage = pageInfo.lastPage ?: 0

                val searchResults = ArrayList<SearchResult>(characters.size)
                characters.forEach { character ->
                    val searchResult = SearchResult.fromCharacter(character) ?: return@forEach

                    searchResults.add(searchResult)
                }
                searchResults.trimToSize()

                return@map SearchResults(
                    AniListType.CHARACTER,
                    searchResults,
                    currentPage,
                    lastPage
                )
            }
            .subscribe({
                searchResultsLiveData.postValue(it)
            }, {
                // TODO throwable live data
                searchResultsLiveData.postValue(null)
            })
    }

    fun searchStaff(page: Int, perPage: Int, search: String, sort: String) {
        searchDisposable = aniSearchRepository.searchStaff(page, perPage, search, sort)
            .map {staffSearchResult ->
                val staffs = staffSearchResult.data?.page?.staffs ?: return@map null
                val pageInfo = staffSearchResult.data?.page?.pageInfo ?: return@map null
                val currentPage = pageInfo.currentPage ?: 0
                val lastPage = pageInfo.lastPage ?: 0

                val searchResults = ArrayList<SearchResult>(staffs.size)
                staffs.forEach { staff ->
                    val searchResult = SearchResult.fromStaff(staff) ?: return@forEach

                    searchResults.add(searchResult)
                }
                searchResults.trimToSize()

                return@map SearchResults(
                    AniListType.STAFF,
                    searchResults,
                    currentPage,
                    lastPage
                )
            }
            .subscribe({
                searchResultsLiveData.postValue(it)
            }, {
                // TODO throwable live data
                searchResultsLiveData.postValue(null)
            })
    }

}