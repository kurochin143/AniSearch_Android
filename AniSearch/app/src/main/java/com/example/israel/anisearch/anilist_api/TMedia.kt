package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TMedia <T> {

    @SerializedName("Media")
    @Expose
    val media: T? = null
}