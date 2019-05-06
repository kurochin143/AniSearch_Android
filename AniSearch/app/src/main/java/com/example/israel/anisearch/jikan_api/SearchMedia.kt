package com.example.israel.anisearch.jikan_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class SearchMedia <T : Media>(
) {
    constructor(results: ArrayList<T>?) : this() {
        _results = results
    }

    @SerializedName("results")
    @Expose
    private var _results: ArrayList<T>? = null

    var results
        get() = _results
        set(value) {_results=value}

}
