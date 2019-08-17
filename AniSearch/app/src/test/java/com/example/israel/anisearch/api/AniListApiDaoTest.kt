package com.example.israel.anisearch.api

import com.example.israel.anisearch.graphql.GraphQLQueryBuilder
import com.example.israel.anisearch.model.data.Anime
import com.example.israel.anisearch.model.data.TPage
import org.junit.Test

class AniListApiDaoTest {

    @Test
    fun getTopAnime_QueryBuilder() {

        val queryBuiltStr = GraphQLQueryBuilder().also {
            it.addObject(
                TPage.createGraphQLObject(1, 1)
                .addObject(Anime.createSearchGraphQLObject("SEARCH_MATCH", false, "hehe")))
        }.build()


        val queryStr =
            "{Page(page: 1, perPage: 1) {media(search: \"hehe\", type: ANIME, sort: SEARCH_MATCH, isAdult: false) {id title{romaji english native userPreferred} description coverImage{large medium} bannerImage}}}"

    }

}