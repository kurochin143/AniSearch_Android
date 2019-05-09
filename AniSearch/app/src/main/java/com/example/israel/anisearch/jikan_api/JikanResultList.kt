package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JikanResultList <T>() {
    constructor(results: Array<T>?) : this() {
        _results = results
    }

    @SerializedName("results", alternate = ["top"])
    @Expose
    private var _results: Array<T>? = null

    var results
        get() = _results
        set(value) {_results=value}

}
