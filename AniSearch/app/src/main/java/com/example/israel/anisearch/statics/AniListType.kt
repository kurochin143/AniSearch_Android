package com.example.israel.anisearch.statics

enum class AniListType {
    ANIME,
    MANGA,
    CHARACTER,
    STAFF;

    companion object {
        fun fromStringArrPosition(position: Int) : AniListType {
            return when (position) {
                0 -> ANIME
                1 -> MANGA
                2 -> CHARACTER
                3 -> STAFF
                else -> ANIME
            }
        }
    }
}