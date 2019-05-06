package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MediaAnime : Media {

    constructor(id: Long?, title: String?, image_url: String?, type: String?, synopsis: String?, episodes: Int?)
            : super(id, title, image_url, type, synopsis) {
        _episodes = episodes

    }

    @SerializedName("episodes")
    @Expose
    private var _episodes: Int? = null

    var episodes
        get() = _episodes
        set(value) {
            _episodes = value
        }
}