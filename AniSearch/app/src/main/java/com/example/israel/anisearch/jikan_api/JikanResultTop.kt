package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JikanResultTop : JikanResult() {

    @SerializedName("rank")
    @Expose
    private var _rank: Int? = null

    var rank
        get() = _rank
        set(value) {_rank=value}
}