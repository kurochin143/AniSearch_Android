package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JikanMediaAnime : JikanMedia() {

    @SerializedName("episodes")
    @Expose
    private var _episodes: Int? = null

    var episodes
        get() = _episodes
        set(value) {
            _episodes = value
        }
}