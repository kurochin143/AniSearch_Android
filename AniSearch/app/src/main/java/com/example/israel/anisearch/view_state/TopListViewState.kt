package com.example.israel.anisearch.view_state

import com.example.israel.anisearch.statics.AniListType

class TopListViewState(
    var isLoading: Boolean = false,
    var aniListType: AniListType = AniListType.ANIME,
    var topList: MutableList<Any> = mutableListOf()
)