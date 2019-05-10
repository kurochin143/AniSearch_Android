package com.example.israel.anisearch.jikan_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class JikanMedia : Jikan() {

    companion object {
        const val RATED_NOT_RATED = "Not rated"
        const val RATED_G = "G"
        const val RATED_PG = "PG"
        const val RATED_PG13 = "PG-13"
        const val RATED_R = "R"
        const val RATED_RPLUS = "R+"
        const val RATED_RX = "Rx"
        const val RATED_ALL_STR = "$RATED_NOT_RATED,$RATED_G,$RATED_PG,$RATED_PG13,$RATED_R,$RATED_RPLUS,$RATED_RX"
    }

    @SerializedName("type")
    @Expose
    private var _type: String? = null

    @SerializedName("synopsis")
    @Expose
    private var _synopsis: String? = null

    @SerializedName("rated")
    @Expose
    private var _rated: String? = null

    var type
        get() = _type
        set(value) {_type=value}

    var synopsis
        get() = _synopsis
        set(value) {_synopsis=value}

    var rated
        get() = _rated?: RATED_NOT_RATED
        set(value) {_rated=value}
}