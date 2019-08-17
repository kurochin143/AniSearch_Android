package com.example.israel.anisearch.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Characters : Page() {

    @SerializedName("characters")
    @Expose
    var characters: ArrayList<Character>? = null

}