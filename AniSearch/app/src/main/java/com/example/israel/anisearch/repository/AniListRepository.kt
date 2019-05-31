package com.example.israel.anisearch.repository

import com.example.israel.anisearch.anilist_api.AniListApiDao
import com.example.israel.anisearch.anilist_api.AnimeSearchResult
import io.reactivex.Observable

class AniListRepository(private val aniListApiDao: AniListApiDao) : AniSearchRepository() {
    override fun getTopAnime(page: Int, perPage: Int): Observable<AnimeSearchResult?> {
        return aniListApiDao.getTopAnime(page, perPage)
    }

}