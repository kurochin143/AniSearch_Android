package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TPage <T: TPage.Page> {

    @SerializedName("Page")
    @Expose
    var page: T? = null

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

            companion object {
                fun createGraphQLObject(): GraphQLObject {
                    return GraphQLObject("pageInfo")
                        .addField("currentPage")
                        .addField("lastPage")
                }
            }
        }
    }

    companion object {
        fun createGraphQLObject(page: Int, perPage: Int): GraphQLObject {
            return GraphQLObject("Page").also {
                it.addParam("page", page.toString())
                it.addParam("perPage", perPage.toString())
                it.addObject(Page.PageInfo.createGraphQLObject())
            }
        }
    }
}