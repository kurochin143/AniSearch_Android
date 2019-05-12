package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject

class Anime : Media() {

    companion object {
        fun createGraphQLObject(sort: String, isAdult: Boolean, search: String?): GraphQLObject {
            return Media.createGraphQLObject("ANIME", sort, isAdult, search).also {
                // TODO
            }
        }
    }

}