package com.example.israel.anisearch.repository

import com.example.israel.anisearch.anilist_api.*
import io.reactivex.Observable
import io.reactivex.Single

class AniSearchRepositoryImpl(private val aniListApiDao: AniListApiDao) : AniSearchRepository() {

    override fun searchAnime(page: Int, perPage: Int, search: String?, sort: String): Observable<AnimeSearchResult> {
        return aniListApiDao.searchAnime(page, perPage, search, sort)
    }

    override fun searchManga(page: Int, perPage: Int, search: String?, sort: String): Observable<MangaSearchResult> {
        return aniListApiDao.searchManga(page, perPage, search, sort)
    }

    override fun searchCharacter(page: Int, perPage: Int, search: String?, sort: String): Observable<CharacterSearchResult> {
        return aniListApiDao.searchCharacter(page, perPage, search, sort)
    }

    override fun searchStaff(page: Int, perPage: Int, search: String?, sort: String): Observable<StaffSearchResult> {
        return aniListApiDao.searchStaff(page, perPage, search, sort)
    }

    override fun getAnimeDetails(id: Int): Observable<AnimeResult> {
        return aniListApiDao.getAnimeDetails(id)
    }

    override fun getMediaCharacters(id: Int, page: Int, perPage: Int, sort: String): Single<MediaResult> {
        return aniListApiDao.getMediaCharacters(id, page, perPage, sort)
    }
}