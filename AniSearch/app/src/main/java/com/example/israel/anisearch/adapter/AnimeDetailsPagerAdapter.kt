package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import com.example.israel.anisearch.model.data.Anime
import com.example.israel.anisearch.fragment.MediaDetailsCharactersFragment
import com.example.israel.anisearch.fragment.AnimeDetailsOverviewFragment
import java.lang.RuntimeException

class AnimeDetailsPagerAdapter(
    fragmentManager: androidx.fragment.app.FragmentManager,
    private val animeDetails: Anime,
    private val image: Bitmap?
) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): androidx.fragment.app.Fragment {
        return when (p0) {
            0 -> AnimeDetailsOverviewFragment.newInstance(animeDetails, image)
            1 -> MediaDetailsCharactersFragment.newInstance(animeDetails.id!!)
            2 -> androidx.fragment.app.Fragment()
            3 -> androidx.fragment.app.Fragment()
            4 -> androidx.fragment.app.Fragment()
            5 -> androidx.fragment.app.Fragment()
            else -> throw RuntimeException("Out of bounds")
        }
    }

    override fun getCount(): Int {
        return 6
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Overview"
            1 -> "Characters"
            2 -> "3"
            3 -> "4"
            4 -> "5"
            5 -> "6"
            else -> throw RuntimeException("Out of bounds")
        }
    }
}