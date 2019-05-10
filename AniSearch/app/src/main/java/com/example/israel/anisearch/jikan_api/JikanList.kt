package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JikanList <T>() {
    constructor(results: ArrayList<T>?) : this() {
        _list = results
    }

    @SerializedName("results", alternate = ["top"])
    @Expose
    private var _list: ArrayList<T>? = null

    var list
        get() = _list
        set(value) {_list=value}

}
