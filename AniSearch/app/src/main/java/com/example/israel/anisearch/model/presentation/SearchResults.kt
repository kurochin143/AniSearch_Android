package com.example.israel.anisearch.model.presentation

import com.example.israel.anisearch.statics.AniListType

class SearchResults(
    var aniListType: AniListType,
    var searchResults: MutableList<SearchResult>,
    var currentPage: Int,
    var lastPage: Int
)