package com.example.israel.anisearch.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class TEdge <T> {

    @SerializedName("node")
    @Expose
    var node: T? = null

}