package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLQuery
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/")
    fun searchAnime(@Body query: GraphQLQuery): Observable<AnimeSearchResult?>

    @POST("/")
    fun searchManga(@Body query: GraphQLQuery): Observable<MangaSearchResult?>

    @POST("/")
    fun searchCharacter(@Body query: GraphQLQuery): Observable<CharacterSearchResult?>
}