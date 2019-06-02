package com.example.israel.anisearch.repository

import com.example.israel.anisearch.anilist_api.AnimeSearchResult
import com.example.israel.anisearch.anilist_api.CharacterSearchResult
import com.example.israel.anisearch.anilist_api.MangaSearchResult
import com.example.israel.anisearch.anilist_api.StaffSearchResult
import io.reactivex.Observable

abstract class AniSearchRepository {
    abstract fun searchAnime(page: Int, perPage: Int, search: String?, sort: String): Observable<AnimeSearchResult?>
    abstract fun searchManga(page: Int, perPage: Int, search: String?, sort: String): Observable<MangaSearchResult?>
    abstract fun searchCharacter(page: Int, perPage: Int, search: String?, sort: String): Observable<CharacterSearchResult?>
    abstract fun searchStaff(page: Int, perPage: Int, search: String?, sort: String): Observable<StaffSearchResult?>

}