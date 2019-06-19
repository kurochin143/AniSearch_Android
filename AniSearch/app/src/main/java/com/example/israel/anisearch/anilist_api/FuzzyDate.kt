package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DateFormatSymbols

class FuzzyDate {

    @SerializedName("year")
    @Expose
    var year: Int? = null

    @SerializedName("month")
    @Expose
    var month: Int? = null

    @SerializedName("day")
    @Expose
    var day: Int? = null

    companion object {
        fun createGraphQLObject(name: String): GraphQLObject {
            return GraphQLObject(name).also {
                it.addField("year")
                it.addField("month")
                it.addField("day")
            }
        }
    }

    override fun toString(): String {
        return buildString {
            val monthL = month ?: return ""
            val dayL = day ?: return ""
            val yearL = year ?: return ""

            append(DateFormatSymbols().months[monthL - 1])
            append(" ")
            append(dayL.toString())
            append(", ")
            append(yearL.toString())
        }
    }
}