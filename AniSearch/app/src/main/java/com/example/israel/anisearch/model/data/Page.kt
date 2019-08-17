package com.example.israel.anisearch.model.data

import com.example.israel.anisearch.graphql.GraphQLObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class Page {

    @SerializedName("pageInfo")
    @Expose
    var pageInfo: PageInfo? = null

    class PageInfo {
        @SerializedName("currentPage")
        @Expose
        var currentPage: Int? = null

        @SerializedName("lastPage")
        @Expose
        var lastPage: Int? = null

        constructor()
        constructor(currentPage: Int?, lastPage: Int?) {
            this.currentPage = currentPage
            this.lastPage = lastPage
        }

        companion object {
            fun createGraphQLObject(): GraphQLObject {
                return GraphQLObject("pageInfo")
                    .addField("currentPage")
                    .addField("lastPage")
            }
        }
    }
}