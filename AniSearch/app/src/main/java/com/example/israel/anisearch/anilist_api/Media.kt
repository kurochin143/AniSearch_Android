package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class Media {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("title")
    @Expose
    var title: Title? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("coverImage")
    @Expose
    var coverImage: CoverImage? = null

    class Title {
        @SerializedName("romaji")
        @Expose
        var romaji: String? = null

        @SerializedName("english")
        @Expose
        var english: String? = null

        @SerializedName("native")
        @Expose
        var native: String? = null

        @SerializedName("userPreferred")
        @Expose
        var userPreferred: String? = null

        companion object {
            fun createGraphQLObject(): GraphQLObject {
                return GraphQLObject("title")
                    .addField("romaji")
                    .addField("english")
                    .addField("native")
                    .addField("userPreferred")
            }
        }
    }

    class CoverImage {
        @SerializedName("medium")
        @Expose
        var medium: String? = null

        @SerializedName("large")
        @Expose
        var large: String? = null

        companion object {
            fun createGraphQLObject(): GraphQLObject {
                return GraphQLObject("coverImage")
                    .addField("medium")
                    .addField("large")
            }
        }
    }

    companion object {
        fun createGraphQLObject(type: String, sort: String, isAdult: Boolean, search: String?): GraphQLObject {
            return GraphQLObject("media").also {
                it.addParam("type", type)
                it.addParam("sort", sort)
                it.addParam("isAdult", isAdult.toString())
                if (search != null) it.addParam("search", "\"$search\"")

                it.addField("id")
                it.addField("type")
                it.addObject(Title.createGraphQLObject())
                it.addObject(CoverImage.createGraphQLObject())
            }
        }

        fun createGraphQLObject() {
            // TODO
        }
    }
}