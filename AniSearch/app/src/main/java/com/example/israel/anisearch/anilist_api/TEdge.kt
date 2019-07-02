package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class TEdge <T> {

    @SerializedName("node")
    @Expose
    var node: T? = null

}