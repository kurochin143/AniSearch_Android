package com.example.israel.anisearch.api

import com.example.israel.anisearch.graphql.GraphQLQuery
import com.example.israel.anisearch.model.data.*
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
    fun searchAnime(@Body query: GraphQLQuery): Single<AnimeSearchResult>

    @POST("/")
    fun searchManga(@Body query: GraphQLQuery): Single<MangaSearchResult>

    @POST("/")
    fun searchCharacter(@Body query: GraphQLQuery): Single<CharacterSearchResult>

    @POST("/")
    fun searchStaff(@Body query: GraphQLQuery): Single<StaffSearchResult>

    @POST("/")
    fun getAnimeDetails(@Body query: GraphQLQuery): Single<AnimeResult>

    @POST("/")
    fun getMediaCharacters(@Body query: GraphQLQuery): Single<MediaResult>

}