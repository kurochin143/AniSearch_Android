package com.example.israel.anisearch.anilist_api

import com.example.israel.anisearch.graphql.GraphQLObject

class CharacterConnection : TConnection<CharacterEdge> {

    constructor()
    constructor(edges: MutableList<CharacterEdge>?, pageInfo: Page.PageInfo?) : super(edges, pageInfo)

    companion object {
        fun createGraphQLObject(page: Int, perPage: Int, sort: String): GraphQLObject {
            return GraphQLObject("characters").also {
                it.addParam("page", page.toString())
                it.addParam("perPage", perPage.toString())
                it.addParam("sort", sort)

                val edgesObject = GraphQLObject("edges")
                edgesObject.addObject(Character.createDetailsGraphQLObject("node"))
                edgesObject.addField("role")
                it.addObject(edgesObject)

                it.addObject(Page.PageInfo.createGraphQLObject())
            }
        }
    }

}