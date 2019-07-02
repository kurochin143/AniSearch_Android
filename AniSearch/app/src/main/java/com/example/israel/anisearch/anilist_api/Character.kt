package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Character {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: Name? = null

    @SerializedName("image")
    @Expose
    var image: Image? = null

    class Name {
        @SerializedName("first")
        @Expose
        var first: String? = null

        @SerializedName("last")
        @Expose
        var last: String? = null

        @SerializedName("native")
        @Expose
        var native: String? = null

        fun getFullName(): String? {
            if (first != null) {
                return first + " " + (last?: "")
            }

            return last
        }

        companion object {
            fun createGraphQLObject(): GraphQLObject {
                return GraphQLObject("name")
                    .addField("first")
                    .addField("last")
                    .addField("native")
            }
        }
    }

    class Image {

        @SerializedName("medium")
        @Expose
        var medium: String? = null

        @SerializedName("large")
        @Expose
        var large: String? = null

        companion object {
            fun createGraphQLObject(): GraphQLObject {
                return GraphQLObject("image")
                    .addField("medium")
                    .addField("large")
            }
        }
    }

    companion object {
        fun createSearchGraphQLObject(sort: String, search: String?): GraphQLObject {
            return GraphQLObject("characters").also {
                it.addParam("sort", sort)
                if (search != null) it.addParam("search", "\"$search\"")

                it.addField("id")
                it.addObject(Name.createGraphQLObject())
                it.addObject(Image.createGraphQLObject())
            }
        }

        fun createDetailsGraphQLObject(name: String): GraphQLObject {
            return GraphQLObject(name).also {
                it.addField("id")
                it.addObject(Name.createGraphQLObject())
                it.addObject(Image.createGraphQLObject())
            }
        }
    }
}