package com.example.israel.anisearch.anilist_api.statics

class AniListType {
    companion object {
        const val ANIME = "ANIME"
        const val MANGA = "MANGA"
        const val CHARACTER = "CHARACTER"
        const val STAFF = "STAFF"

        fun fromStringArrPosition(position: Int) : String {
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