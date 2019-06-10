package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.anilist_api.details.AnimeDetails
import com.example.israel.anisearch.graphql.GraphQLQuery
import com.example.israel.anisearch.graphql.GraphQLQueryBuilder
import io.reactivex.Observable

class AniListApiDao(private var apiService: ApiService) {
    fun searchAnime(page: Int, perPage: Int, search: String?, sort: String): Observable<AnimeSearchResult?> {
        // "{Page(page: $page, perPage: $perPage) {media(search: \"$query\", type: ANIME, sort: $sort, isAdult: false) {id title{romaji english native userPreferred} description coverImage{large medium} bannerImage}}}"
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Anime.createSearchGraphQLObject(sort, false, search))
        )
        return apiService.searchAnime(GraphQLQuery(queryBuilder.build()))
    }

    fun searchManga(page: Int, perPage: Int, search: String?, sort: String): Observable<MangaSearchResult?> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Manga.createGraphQLObject(sort, false, search))
        )
        return apiService.searchManga(GraphQLQuery(queryBuilder.build()))
    }

    fun searchCharacter(page: Int, perPage: Int, search: String?, sort: String): Observable<CharacterSearchResult?> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Character.createGraphQLObject(sort, search))
        )
        return apiService.searchCharacter(GraphQLQuery(queryBuilder.build()))
    }

    fun searchStaff(page: Int, perPage: Int, search: String?, sort: String): Observable<StaffSearchResult?> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Staff.createGraphQLObject(sort, search))
        )
        return apiService.searchStaff(GraphQLQuery(queryBuilder.build()))
    }

    fun getAnimeDetails(id: Int): Observable<AnimeDetailsResult?> {
        val queryBuilder = GraphQLQueryBuilder()
            .addObject(AnimeDetails.createDetailsGraphQLObject(id, false))

        return apiService.getAnimeDetails(GraphQLQuery(queryBuilder.build()))
    }

}