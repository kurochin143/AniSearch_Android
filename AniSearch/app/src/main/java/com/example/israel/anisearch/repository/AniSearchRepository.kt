package com.example.israel.anisearch.repository

import com.example.israel.anisearch.anilist_api.AnimeSearchResult
import io.reactivex.Observable

abstract class AniSearchRepository {
    abstract fun getTopAnime(page: Int, perPage: Int) : Observable<AnimeSearchResult?>
}