package com.example.israel.anisearch.model.data

import com.example.israel.anisearch.graphql.GraphQLObject

class Manga : Media() {
    companion object {
        fun createGraphQLObject(sort: String, isAdult: Boolean, search: String?): GraphQLObject {
            return createSearchGraphQLObject(
                "MANGA",
                sort,
                isAdult,
                search
            ).also {
            }
        }
    }
}