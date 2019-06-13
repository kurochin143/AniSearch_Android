package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject

class Manga : Media() {
    companion object {
        fun createGraphQLObject(sort: String, isAdult: Boolean, search: String?): GraphQLObject {
            return Media.createSearchGraphQLObject("MANGA", sort, isAdult, search).also {
            }
        }
    }
}