package com.example.israel.anisearch.repository

import com.example.israel.anisearch.api.*
import com.example.israel.anisearch.graphql.GraphQLQuery
import com.example.israel.anisearch.graphql.GraphQLQueryBuilder
import com.example.israel.anisearch.model.data.*
import io.reactivex.Single

class AniSearchRepositoryImpl(private val aniListApiService: AniListApiService) : AniSearchRepository() {

    override fun searchAnime(page: Int, perPage: Int, search: String?, sort: String): Single<AnimeSearchResult> {
        // "{Page(page: $page, perPage: $perPage) {media(search: \"$query\", type: ANIME, sort: $sort, isAdult: false) {id title{romaji english native userPreferred} description coverImage{large medium} bannerImage}}}"
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Anime.createSearchGraphQLObject(sort, false, search))
        )
        return aniListApiService.searchAnime(GraphQLQuery(queryBuilder.build()))
    }

    override fun searchManga(page: Int, perPage: Int, search: String?, sort: String): Single<MangaSearchResult> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Manga.createGraphQLObject(sort, false, search))
        )
        return aniListApiService.searchManga(GraphQLQuery(queryBuilder.build()))
    }

    override fun searchCharacter(page: Int, perPage: Int, search: String?, sort: String): Single<CharacterSearchResult> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Character.createSearchGraphQLObject(sort, search))
        )
        return aniListApiService.searchCharacter(GraphQLQuery(queryBuilder.build()))
    }

    override fun searchStaff(page: Int, perPage: Int, search: String?, sort: String): Single<StaffSearchResult> {
        val queryBuilder = GraphQLQueryBuilder().addObject(
            TPage.createGraphQLObject(page, perPage)
                .addObject(Staff.createSearchGraphQLObject(sort, search))
        )
        return aniListApiService.searchStaff(GraphQLQuery(queryBuilder.build()))
    }

    override fun getAnimeDetails(id: Int): Single<AnimeResult> {
        val queryBuilder = GraphQLQueryBuilder()
            .addObject(Anime.createDetailsGraphQLObject("Media", id))

        return aniListApiService.getAnimeDetails(GraphQLQuery(queryBuilder.build()))
    }

    override fun getMediaCharacters(id: Int, page: Int, perPage: Int, sort: String): Single<MediaResult> {
        val queryBuilder = GraphQLQueryBuilder()
            .addObject(Media.createCharactersGraphQLObject(id, page, perPage, sort))

        return aniListApiService.getMediaCharacters(GraphQLQuery(queryBuilder.build()))
    }
}