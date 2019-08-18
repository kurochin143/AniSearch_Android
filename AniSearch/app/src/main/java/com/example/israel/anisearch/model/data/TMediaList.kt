package com.example.israel.anisearch.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TMediaList <T : Media> : Page() {

    @SerializedName("media")
    @Expose
    var media: MutableList<T>? = null

}