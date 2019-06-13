package com.example.israel.anisearch.anilist_api.details

import com.example.israel.anisearch.graphql.GraphQLObject

class AnimeDetails : MediaDetails() {
    companion object {
        fun createGraphQLObject(id: Int, isAdult: Boolean): GraphQLObject {
            return createGraphQLObject(id,"ANIME", isAdult)
        }
    }
}