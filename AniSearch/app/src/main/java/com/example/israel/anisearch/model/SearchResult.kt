package com.example.israel.anisearch.model

import com.example.israel.anisearch.anilist_api.Anime
import com.example.israel.anisearch.anilist_api.Character
import com.example.israel.anisearch.anilist_api.Manga
import com.example.israel.anisearch.anilist_api.Staff
import com.example.israel.anisearch.anilist_api.statics.AniListType

class SearchResult(
    var type: String,
    var id: Int,
    var name: String?,
    var imageUrl: String?
) {
    companion object {
        const val NO_NAME = "NO_NAME"

        fun fromAnime(anime: Anime): SearchResult? {
            val id = anime.id ?: return null
            val title = anime.title
            val name = if (title == null) {
                NO_NAME
            } else {
                title.english ?: title.romaji ?: title.native ?: NO_NAME
            }

            return SearchResult(AniListType.ANIME, id, name, anime.coverImage?.medium)
        }

        fun fromManga(manga: Manga): SearchResult? {
            val id = manga.id ?: return null
            val title = manga.title
            val name = if (title == null) {
                NO_NAME
            } else {
                title.english ?: title.romaji ?: title.native ?: NO_NAME
            }

            return SearchResult(AniListType.MANGA, id, name, manga.coverImage?.medium)
        }

        fun fromCharacter(character: Character): SearchResult? {
            val id = character.id ?: return null
            val characterName = character.name
            val name = if (characterName == null) {
                NO_NAME
            } else {
                characterName.getFullName() ?: characterName.native ?: NO_NAME
            }

            return SearchResult(AniListType.CHARACTER, id, name, character.image?.medium)
        }

        fun fromStaff(staff: Staff): SearchResult? {
            val id = staff.id ?: return null
            val staffName = staff.name
            val name = if (staffName == null) {
                NO_NAME
            } else {
                staffName.getFullName() ?: staffName.native ?: NO_NAME
            }

            return SearchResult(AniListType.CHARACTER, id, name, staff.image?.medium)
        }
    }

}