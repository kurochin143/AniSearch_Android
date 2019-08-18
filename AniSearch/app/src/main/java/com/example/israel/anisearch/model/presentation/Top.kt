package com.example.israel.anisearch.model.presentation

import com.example.israel.anisearch.model.data.Anime
import com.example.israel.anisearch.model.data.Character
import com.example.israel.anisearch.model.data.Manga
import com.example.israel.anisearch.model.data.Staff
import com.example.israel.anisearch.statics.AniListType

class Top(
    var aniListType: AniListType,
    var id: Int,
    var name: String,
    var imageUrl: String?
) {
    companion object {
        const val NO_NAME = "NO_NAME"

        fun fromAnime(anime: Anime): Top? {
            val id = anime.id ?: return null
            val title = anime.title
            val name = if (title == null) {
                NO_NAME
            } else {
                title.english ?: title.romaji ?: title.native ?: NO_NAME
            }

            return Top(
                AniListType.ANIME,
                id,
                name,
                anime.coverImage?.medium
            )
        }

        fun fromManga(manga: Manga): Top? {
            val id = manga.id ?: return null
            val title = manga.title
            val name = if (title == null) {
                NO_NAME
            } else {
                title.english ?: title.romaji ?: title.native ?: NO_NAME
            }

            return Top(
                AniListType.MANGA,
                id,
                name,
                manga.coverImage?.medium
            )
        }

        fun fromCharacter(character: Character): Top? {
            val id = character.id ?: return null
            val characterName = character.name
            val name = if (characterName == null) {
                NO_NAME
            } else {
                characterName.getFullName() ?: characterName.native ?: NO_NAME
            }

            return Top(
                AniListType.CHARACTER,
                id,
                name,
                character.image?.medium
            )
        }

        fun fromStaff(staff: Staff): Top? {
            val id = staff.id ?: return null
            val staffName = staff.name
            val name = if (staffName == null) {
                NO_NAME
            } else {
                staffName.getFullName() ?: staffName.native ?: NO_NAME
            }

            return Top(AniListType.STAFF, id, name, staff.image?.medium)
        }
    }
}