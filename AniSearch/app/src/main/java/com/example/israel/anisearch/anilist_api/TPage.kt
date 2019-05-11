package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TPage <T> {

    @SerializedName("Page")
    @Expose
    var page: T? = null
}