package com.example.israel.anisearch.anilist_api

enum class MediaSearchSort {
    SEARCH_MATCH("SEARCH_MATCH"),
    SCORE_DESC("SCORE_DESC");

    val value: String

    private constructor(value: String) {
        this.value = value
    }

}