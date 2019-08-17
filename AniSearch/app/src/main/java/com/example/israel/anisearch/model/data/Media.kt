package com.example.israel.anisearch.model.data

import com.example.israel.anisearch.graphql.GraphQLObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// TODO parcelable, don't forget member variables
open class Media : Serializable {

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

    @SerializedName("format")
    @Expose
    var format: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("startDate")
    @Expose
    var startDate: FuzzyDate? = null

    @SerializedName("endDate")
    @Expose
    var endDate: FuzzyDate? = null

    @SerializedName("genres")
    @Expose
    var genres: MutableList<String>? = null

    @SerializedName("averageScore")
    @Expose
    var averageScore: Int? = null

    @SerializedName("characters")
    @Expose
    var characterConnection: CharacterConnection? = null

    class Title : Serializable {
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

    class CoverImage : Serializable {
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
        fun createSearchGraphQLObject(type: String, sort: String?, isAdult: Boolean, search: String?): GraphQLObject {
            return GraphQLObject("media").also {
                it.addParam("type", type)
                if (sort != null) it.addParam("sort", sort)
                it.addParam("isAdult", isAdult.toString())
                if (search != null) it.addParam("search", "\"$search\"")

                it.addField("id")
                it.addField("type")
                it.addObject(Title.createGraphQLObject())
                it.addObject(CoverImage.createGraphQLObject())
            }
        }

        fun createDetailsGraphQLObject(name: String, id: Int): GraphQLObject {
            return GraphQLObject(name).also {
                it.addParam("id", id.toString())

                it.addField("id")
                it.addField("type")
                it.addObject(Title.createGraphQLObject())
                it.addObject(CoverImage.createGraphQLObject())
                it.addField("format")
                it.addField("status")
                it.addObject(GraphQLObject("description").addParam("asHtml", "false"))
                it.addObject(FuzzyDate.createGraphQLObject("startDate"))
                it.addObject(FuzzyDate.createGraphQLObject("endDate"))
                it.addField("genres")
                it.addField("averageScore")
            }
        }

        fun createCharactersGraphQLObject(id: Int, page: Int, perPage: Int, sort: String): GraphQLObject {
            return GraphQLObject("Media").also {
                it.addParam("id", id.toString())

                it.addObject(
                    CharacterConnection.createGraphQLObject(
                        page,
                        perPage,
                        sort
                    )
                )
            }
        }
    }
}