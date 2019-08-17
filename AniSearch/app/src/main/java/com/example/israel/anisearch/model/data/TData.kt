package com.example.israel.anisearch.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TData <T> {

    @SerializedName("data")
    @Expose
    var data: T? = null

}