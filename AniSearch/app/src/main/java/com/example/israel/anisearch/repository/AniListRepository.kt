package com.example.israel.anisearch.repository

import com.example.israel.anisearch.anilist_api.*
import io.reactivex.Observable

class AniListRepository(private val aniListApiDao: AniListApiDao) : AniSearchRepository() {

    override fun getTopAnime(page: Int, perPage: Int): Observable<AnimeSearchResult?> {
        return aniListApiDao.getTopAnime(page, perPage)
    }

    override fun getTopManga(page: Int, perPage: Int): Observable<MangaSearchResult?> {
        return aniListApiDao.getTopManga(page, perPage)
    }

    override fun getTopCharacter(page: Int, perPage: Int): Observable<CharacterSearchResult?> {
        return aniListApiDao.getTopCharacters(page, perPage)
    }

    override fun searchAnime(
        page: Int,
        perPage: Int,
        search: String,
        searchSort: MediaSearchSort
    ): Observable<AnimeSearchResult?> {
        return aniListApiDao.searchAnime(page, perPage, search, searchSort)
    }

}