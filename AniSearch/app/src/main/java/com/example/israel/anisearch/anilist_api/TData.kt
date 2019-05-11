package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TData <T> {

    @SerializedName("data")
    @Expose
    var data: T? = null

}