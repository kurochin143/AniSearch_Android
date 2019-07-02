package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.israel.anisearch.anilist_api.Anime
import com.example.israel.anisearch.fragment.MediaDetailsCharactersFragment
import com.example.israel.anisearch.fragment.AnimeDetailsOverviewFragment
import java.lang.RuntimeException

class AnimeDetailsPagerAdapter(
    fragmentManager: FragmentManager,
    private val animeDetails: Anime,
    private val image: Bitmap?
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> AnimeDetailsOverviewFragment.newInstance(animeDetails, image)
            1 -> MediaDetailsCharactersFragment.newInstance(animeDetails.id!!)
            2 -> Fragment()
            3 -> Fragment()
            4 -> Fragment()
            5 -> Fragment()
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