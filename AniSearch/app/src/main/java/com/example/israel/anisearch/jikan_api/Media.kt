package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class Media {

    @SerializedName("type")
    @Expose
    private var _type: String? = null
    @SerializedName("synopsis")
    @Expose
    private var _synopsis: String? = null

    var type
        get() = _type
        set(value) {_type=value}

    var synopsis
        get() = _synopsis
        set(value) {_synopsis=value}

}