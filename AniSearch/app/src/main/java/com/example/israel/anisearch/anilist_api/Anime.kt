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

        fun createDetailsGraphQLObject(name: String, id: Int): GraphQLObject {
            return Media.createDetailsGraphQLObject(name, id).also {
                it.addField("episodes")
                it.addField("duration")
            }
        }
    }

}