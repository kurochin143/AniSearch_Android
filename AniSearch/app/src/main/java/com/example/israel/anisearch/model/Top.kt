package com.example.israel.anisearch.model

import com.example.israel.anisearch.anilist_api.Anime
import com.example.israel.anisearch.anilist_api.Character
import com.example.israel.anisearch.anilist_api.Manga
import com.example.israel.anisearch.anilist_api.Staff
import com.example.israel.anisearch.anilist_api.statics.AniListType

class Top(
    var type: String,
    var id: Int,
    var name: String,
    var imageUrl: String?
) {
    companion object {
        const val NO_NAME = "NO_NAME"

        fun fromAnime(anime: Anime): Top? {
            val id = anime.id ?: return null
            val name = anime.title?.english ?: NO_NAME

            return Top(AniListType.ANIME, id, name, anime.coverImage?.medium)
        }

        fun fromManga(manga: Manga): Top? {
            val id = manga.id ?: return null
            val name = manga.title?.english ?: NO_NAME

            return Top(AniListType.MANGA, id, name, manga.coverImage?.medium)
        }

        fun fromCharacter(character: Character): Top? {
            val id = character.id ?: return null
            val name = character.name?.getFullName() ?: NO_NAME

            return Top(AniListType.CHARACTER, id, name, character.image?.medium)
        }

        fun fromStaff(staff: Staff): Top? {
            val id = staff.id ?: return null
            val name = staff.name?.getFullName() ?: NO_NAME

            return Top(AniListType.STAFF, id, name, staff.image?.medium)
        }
    }
}