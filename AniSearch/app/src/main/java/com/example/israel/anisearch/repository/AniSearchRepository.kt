package com.example.israel.anisearch.repository

import com.example.israel.anisearch.anilist_api.AnimeSearchResult
import com.example.israel.anisearch.anilist_api.CharacterSearchResult
import com.example.israel.anisearch.anilist_api.MangaSearchResult
import com.example.israel.anisearch.anilist_api.MediaSearchSort
import io.reactivex.Observable

abstract class AniSearchRepository {
    abstract fun getTopAnime(page: Int, perPage: Int) : Observable<AnimeSearchResult?>
    abstract fun getTopManga(page: Int, perPage: Int) : Observable<MangaSearchResult?>
    abstract fun getTopCharacter(page: Int, perPage: Int) : Observable<CharacterSearchResult?>
    //abstract fun getTopStaff(page: Int, perPage: Int) : Observable<StaffSearchResult?>

    abstract fun searchAnime(page: Int, perPage: Int, search: String, searchSort: MediaSearchSort): Observable<AnimeSearchResult?>

}