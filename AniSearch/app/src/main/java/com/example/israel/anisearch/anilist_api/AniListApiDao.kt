package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLQuery
import com.example.israel.anisearch.graphql.GraphQLQueryBuilder
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

typealias AnimeSearchResult = TData<TPage<TMediaList<Anime>>>
typealias TopAnimeResult = TData<TPage<TMediaList<Anime>>>

class AniListApiDao {

    companion object {
        private const val BASE_URL = "https://graphql.anilist.co"
        private const val READ_TIMEOUT = 3000L
        private const val CONNECT_TIMEOUT = 3000L

        private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
            .client(okHttpClient)
            .build()

        val apiService: AniListApiInterface = retrofit.create(
            AniListApiInterface::class.java)

        fun searchAnime(page: Int, perPage: Int, search: String, sort: String = "SEARCH_MATCH"): Call<AnimeSearchResult?> {
            // "{Page(page: $page, perPage: $perPage) {media(search: \"$query\", type: ANIME, sort: $sort, isAdult: false) {id title{romaji english native userPreferred} description coverImage{large medium} bannerImage}}}"
            val queryBuilder = GraphQLQueryBuilder().addObject(
                TPage.createGraphQLObject(page, perPage)
                    .addObject(Anime.createGraphQLObject(sort, false, search))
            )
            return apiService.searchMedia(GraphQLQuery(queryBuilder.build()))
        }

        fun getTopAnime(page: Int, perPage: Int): Call<TopAnimeResult?> {
            val queryBuilder = GraphQLQueryBuilder().addObject(
                TPage.createGraphQLObject(page, perPage)
                    .addObject(Anime.createGraphQLObject("SCORE_DESC", false, null))
            )
            return apiService.searchMedia(GraphQLQuery(queryBuilder.build()))
        }

    }

    interface AniListApiInterface {
        @POST("/")
        fun searchMedia(@Body query: GraphQLQuery): Call<AnimeSearchResult?>
    }
}