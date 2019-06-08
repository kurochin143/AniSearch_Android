package com.example.israel.anisearch.graphql

import java.lang.StringBuilder

class GraphQLQueryBuilder {

    private val graphQLObjects = ArrayList<GraphQLObject>()

    fun addObject(`object`: GraphQLObject): GraphQLQueryBuilder {
        graphQLObjects.add(`object`)
        return this
    }

    fun build(): String {
        buildString {
            append("query {")

            graphQLObjects.forEach {
                append(it.toString())
                append(" ")
            }

            append("}")

            return toString()
        }

        return ""
    }
}