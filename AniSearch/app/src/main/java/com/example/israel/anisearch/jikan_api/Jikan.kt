package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/** Jikan common data*/
open class Jikan() {

    constructor(id: Long?, title: String?, imageUrl: String?) : this() {
        _id = id
        _title = title
        _imageUrl = imageUrl
    }

    @SerializedName("mal_id")
    @Expose
    private var _id: Long? = null

    @SerializedName("title")
    @Expose
    private var _title: String? = null

    @SerializedName("image_url")
    @Expose
    private var _imageUrl: String? = null

    var id
        get() = _id
        set(value) {_id=value}

    var title
        get() = _title
        set(value) {_title=value}

    var imageUrl
        get() = _imageUrl
        set(value) {_imageUrl=value}

}
