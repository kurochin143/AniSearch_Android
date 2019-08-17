package com.example.israel.anisearch.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CharacterEdge : TEdge<Character>() {

    @SerializedName("role")
    @Expose
    var role: String? = null
}