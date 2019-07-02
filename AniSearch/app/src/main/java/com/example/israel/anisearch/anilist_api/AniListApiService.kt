package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLQuery
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

typealias AnimeSearchResult = TData<TPage<TMediaList<Anime>>>
typealias MangaSearchResult = TData<TPage<TMediaList<Manga>>>
typealias CharacterSearchResult = TData<TPage<Characters>>
typealias StaffSearchResult = TData<TPage<Staffs>>

typealias AnimeResult = TData<TMedia<Anime>>

typealias MediaResult = TData<TMedia<Media>>

interface AniListApiService {
    @POST("/")
    fun searchAnime(@Body query: GraphQLQuery): Observable<AnimeSearchResult>

    @POST("/")
    fun searchManga(@Body query: GraphQLQuery): Observable<MangaSearchResult>

    @POST("/")
    fun searchCharacter(@Body query: GraphQLQuery): Observable<CharacterSearchResult>

    @POST("/")
    fun searchStaff(@Body query: GraphQLQuery): Observable<StaffSearchResult>

    @POST("/")
    fun getAnimeDetails(@Body query: GraphQLQuery): Observable<AnimeResult>

    @POST("/")
    fun getMediaCharacters(@Body query: GraphQLQuery): Single<MediaResult>

}