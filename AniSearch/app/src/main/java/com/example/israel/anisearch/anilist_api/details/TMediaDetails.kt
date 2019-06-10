package com.example.israel.anisearch.anilist_api.details

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TMediaDetails <T: MediaDetails> {

    @SerializedName("Media")
    @Expose
    var media: T? = null
}