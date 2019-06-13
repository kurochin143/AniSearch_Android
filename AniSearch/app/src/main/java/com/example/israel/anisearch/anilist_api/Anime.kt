package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject

class Anime : Media() {

    companion object {
        fun createGraphQLObject(sort: String, isAdult: Boolean, search: String?): GraphQLObject {
            return createGraphQLObject("ANIME", sort, isAdult, search).also {
            }
        }
    }

}