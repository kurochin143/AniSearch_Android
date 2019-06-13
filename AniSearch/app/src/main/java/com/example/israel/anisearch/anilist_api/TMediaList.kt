package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TMediaList <T : Media> : Page() {

    @SerializedName("media")
    @Expose
    var media: ArrayList<T>? = null

}