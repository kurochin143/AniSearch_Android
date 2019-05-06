package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MediaManga : Media {

    constructor(id: Long?, title: String?, image_url: String?, type: String?, synopsis: String?, chapters: Int?, volumes: Int?)
            : super(id, title, image_url, type, synopsis) {
        _chapters = chapters
        _volumes = volumes
    }

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