package com.example.israel.anisearch.fragment


import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout

import com.example.israel.anisearch.R
import com.example.israel.anisearch.anilist_api.Anime
import kotlinx.android.synthetic.main.fragment_anime_details_overview.*
import kotlinx.android.synthetic.main.item_anime_details_genre.view.*

class AnimeDetailsOverviewFragment : androidx.fragment.app.Fragment() {

    private lateinit var animeDetails: Anime
    private var image: Bitmap? = null

    companion object {
        private const val ARG_ANIME_DETAILS = "anime_details"
        private const val ARG_IMAGE = "image"

        @JvmStatic
        fun newInstance(animeDetails: Anime, image: Bitmap?) =
            AnimeDetailsOverviewFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ANIME_DETAILS, animeDetails)
                    putParcelable(ARG_IMAGE, image)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animeDetails = it.getSerializable(ARG_ANIME_DETAILS) as Anime
            image = it.getParcelable(ARG_IMAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_details_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_anime_details_i_image.setImageBitmap(image)

        setViewData()

    }

    private fun setViewData() {
        f_anime_details_t_name_english.text = animeDetails.title?.english ?: ""

        f_anime_details_t_format.text = animeDetails.format ?: ""
        f_anime_details_t_status.text = animeDetails.status ?: ""
        f_anime_details_t_description.text = animeDetails.description ?: ""
        f_anime_details_t_start_date.text = animeDetails.startDate?.toString() ?: ""
        f_anime_details_t_end_date.text = animeDetails.endDate?.toString() ?: ""
        f_anime_details_t_episodes.text = animeDetails.episodes?.toString() ?: ""
        val durationL = animeDetails.duration
        f_anime_details_t_duration.text = if (durationL != null) durationL.toString() + "minutes" else ""

        animeDetails.genres?.forEach { genre ->
            val itemView = View.inflate(context, R.layout.item_anime_details_genre, null)
            itemView.i_anime_details_t_genre.text = genre
            f_anime_details_gl_genres.addView(itemView)
            (itemView.layoutParams as GridLayout.LayoutParams).columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        }
    }
}
