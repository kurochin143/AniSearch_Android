package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class Media(
    @SerializedName("mal_id")
    @Expose
    private var _id: Long?,
    @SerializedName("title")
    @Expose
    private var _title: String?,
    @SerializedName("image_url")
    @Expose
    private var _image_url: String?,
    @SerializedName("type")
    @Expose
    private var _type: String?,
    @SerializedName("synopsis")
    @Expose
    private var _synopsis: String?
) {

    var id
        get() = _id
        set(value) {_id=value}

    var title
        get() = _title
        set(value) {_title=value}

    var image_url
        get() = _image_url
        set(value) {_image_url=value}

    var type
        get() = _type
        set(value) {_type=value}

    var synopsis
        get() = _synopsis
        set(value) {_synopsis=value}

}