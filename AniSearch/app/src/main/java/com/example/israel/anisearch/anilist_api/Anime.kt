package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Anime : Media() {

    @SerializedName("episodes")
    @Expose
    var episodes: Int? = null

    @SerializedName("duration")
    @Expose
    var duration: Int? = null

    companion object {
        fun createSearchGraphQLObject(sort: String, isAdult: Boolean, search: String?): GraphQLObject {
            return createSearchGraphQLObject("ANIME", sort, isAdult, search)
        }

        fun createDetailsGraphQLObject(id: Int, isAdult: Boolean): GraphQLObject {
            return createDetailsGraphQLObject(id, "ANIME", isAdult).also {
                it.addField("episodes")
                it.addField("duration")
            }
        }
    }

}