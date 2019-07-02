package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class TConnection <T: TEdge<*>> {

    @SerializedName("edges")
    @Expose
    var edges: MutableList<T>? = null

    @SerializedName("pageInfo")
    @Expose
    var pageInfo: Page.PageInfo? = null

    constructor()
    constructor(edges: MutableList<T>?, pageInfo: Page.PageInfo?) {
        this.edges = edges
        this.pageInfo = pageInfo
    }

}