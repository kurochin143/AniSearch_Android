package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLQuery
import com.example.israel.anisearch.graphql.GraphQLQueryBuilder
import org.junit.Test

class AniListApiDaoTest {

    private fun <T> kany(type: Class<T>): T = any<T>(type)

    @Test
    fun getTopAnime_QueryBuilder() {

        val g = kany(GraphQLQuery::class.java)

        val queryBuiltStr = GraphQLQueryBuilder().also {
            it.addObject(TPage.createGraphQLObject(1, 1)
                .addObject(Anime.createSearchGraphQLObject("SEARCH_MATCH", false, "hehe")))
        }.build()


        val queryStr =
            "{Page(page: 1, perPage: 1) {media(search: \"hehe\", type: ANIME, sort: SEARCH_MATCH, isAdult: false) {id title{romaji english native userPreferred} description coverImage{large medium} bannerImage}}}"

    }

}