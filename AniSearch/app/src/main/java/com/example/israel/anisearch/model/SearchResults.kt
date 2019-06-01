package com.example.israel.anisearch.model

data class SearchResults(
    var type: String,
    var searchResults: MutableList<SearchResult>,
    var currentPage: Int,
    var lastPage: Int
)