package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TMedia <T: Media> {

    @SerializedName("Media")
    @Expose
    var media: T? = null
}