package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class Media {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("title")
    @Expose
    var title: Title? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("coverImage")
    @Expose
    var coverImage: CoverImage? = null

    class Title {
        @SerializedName("romaji")
        @Expose
        var romaji: String? = null

        @SerializedName("english")
        @Expose
        var english: String? = null

        @SerializedName("native")
        @Expose
        var native: String? = null

        @SerializedName("userPreferred")
        @Expose
        var userPreferred: String? = null
    }

    class CoverImage {
        @SerializedName("medium")
        @Expose
        var medium: String? = null

        @SerializedName("large")
        @Expose
        var large: String? = null

    }
}