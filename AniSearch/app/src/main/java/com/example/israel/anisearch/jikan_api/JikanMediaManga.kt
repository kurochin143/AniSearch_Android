package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JikanMediaManga : JikanMedia() {

    @SerializedName("chapters")
    @Expose
    private var _chapters: Int? = null

    @SerializedName("volumes")
    @Expose
    private var _volumes: Int? = null

    var chapters
        get() = _chapters
        set(value) {_chapters = value}

    var volumes
        get() = _volumes
        set(value) {_volumes = value}

}