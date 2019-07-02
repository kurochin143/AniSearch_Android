package com.example.israel.anisearch.repository

import com.example.israel.anisearch.anilist_api.*
import io.reactivex.Observable
import io.reactivex.Single

abstract class AniSearchRepository {
    abstract fun searchAnime(page: Int, perPage: Int, search: String?, sort: String): Single<AnimeSearchResult>
    abstract fun searchManga(page: Int, perPage: Int, search: String?, sort: String): Single<MangaSearchResult>
    abstract fun searchCharacter(page: Int, perPage: Int, search: String?, sort: String): Single<CharacterSearchResult>
    abstract fun searchStaff(page: Int, perPage: Int, search: String?, sort: String): Single<StaffSearchResult>

    abstract fun getAnimeDetails(id: Int): Single<AnimeResult>

    abstract fun getMediaCharacters(id: Int, page: Int, perPage: Int, sort: String): Single<MediaResult>

}