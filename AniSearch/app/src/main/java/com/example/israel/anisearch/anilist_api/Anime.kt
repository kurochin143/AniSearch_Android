package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject

class Anime : Media() {

    companion object {
        fun createSearchGraphQLObject(sort: String, isAdult: Boolean, search: String?): GraphQLObject {
            return createSearchGraphQLObject("ANIME", sort, isAdult, search).also {
            }
        }

        fun createDetailsGraphQLObject(id: Int, isAdult: Boolean): GraphQLObject {
            return createDetailsGraphQLObject(id, "ANIME", isAdult)
        }
    }

}