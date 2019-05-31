package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLQuery
import com.example.israel.anisearch.graphql.GraphQLQueryBuilder
import io.reactivex.Observable

typealias AnimeSearchResult = TData<TPage<TMediaList<Anime>>>

typealias MangaSearchResult = TData<TPage<TMediaList<Manga>>>

typealias CharacterSearchResult = TData<TPage<Characters>>

class AniListApiDao(private var apiService: ApiService) {

    fun searchAnime(page: Int, perPage: Int, search: String, sort: String = "SEARCH_MATCH"): Observable<AnimeSearchResult?> {
        // "{Page(page: $page, perPage: $perPage) {media(search: \"$query\", type: ANIME, sort: $sort, isAdult: false) {id title{romaji english native userPreferred} description coverImage{large medium} bannerImage}}}"
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Anime.createGraphQLObject(sort, false, search))
        )
        return apiService.searchAnime(GraphQLQuery(queryBuilder.build()))
    }

    fun getTopAnime(page: Int, perPage: Int): Observable<AnimeSearchResult?> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Anime.createGraphQLObject("SCORE_DESC", false, null))
        )
        return apiService.searchAnime(GraphQLQuery(queryBuilder.build()))
    }

    fun searchManga(page: Int, perPage: Int, search: String, sort: String = "SEARCH_MATCH"): Observable<MangaSearchResult?> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Manga.createGraphQLObject(sort, false, search))
        )
        return apiService.searchManga(GraphQLQuery(queryBuilder.build()))
    }

    fun getTopManga(page: Int, perPage: Int): Observable<MangaSearchResult?> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Manga.createGraphQLObject("SCORE_DESC", false, null))
        )
        return apiService.searchManga(GraphQLQuery(queryBuilder.build()))
    }

    fun getTopCharacters(page: Int, perPage: Int): Observable<CharacterSearchResult?> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Character.createGraphQLObject("FAVOURITES_DESC", null))
        )
        return apiService.searchCharacter(GraphQLQuery(queryBuilder.build()))
    }
}