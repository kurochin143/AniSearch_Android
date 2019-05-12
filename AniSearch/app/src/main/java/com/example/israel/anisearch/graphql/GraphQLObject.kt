package com.example.israel.anisearch.graphql

class GraphQLObject {

    private val name: String
    private val params = HashMap<String, String>()
    private val fields = ArrayList<String>()
    private val objects = ArrayList<GraphQLObject>()

    constructor(name: String) {
        this.name = name
    }

    fun addParam(key: String, value: String): GraphQLObject {
        params[key] = value
        return this
    }

    fun addField(field: String): GraphQLObject {
        fields.add(field)
        return this
    }

    fun addObject(`object`: GraphQLObject): GraphQLObject {
        objects.add(`object`)
        return this
    }

    override fun toString(): String = buildString {
        append(name)
        if (params.size != 0) {
            append('(')

            params.forEach {
                append(it.key)
                append(':')
                append(it.value)
                append(',')
            }

            setLength(length - 1)

            append(')')
        }

        append('{')

        fields.forEach {
            append(it)
            append(" ")
        }

        objects.forEach {
            append(it.toString())
            append(" ")
        }

        append('}')

        return toString()
    }
}