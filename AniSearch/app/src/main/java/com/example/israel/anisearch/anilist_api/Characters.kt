package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Characters : TPage.Page() {

    @SerializedName("characters")
    @Expose
    var characters: ArrayList<Character>? = null

}