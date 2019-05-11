package com.example.israel.anisearch.graphql

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GraphQLQuery(
    @SerializedName("query")
    @Expose
    var query: String)