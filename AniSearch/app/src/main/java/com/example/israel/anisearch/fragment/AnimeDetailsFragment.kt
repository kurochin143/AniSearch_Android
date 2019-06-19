package com.example.israel.anisearch.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast

import com.example.israel.anisearch.R
import com.example.israel.anisearch.anilist_api.Character
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.view_model.AnimeDetailsViewModel
import com.example.israel.anisearch.view_model.factory.AnimeDetailsVMFactory
import kotlinx.android.synthetic.main.fragment_anime_details.*
import kotlinx.android.synthetic.main.item_anime_details_genre.view.*
import javax.inject.Inject

class AnimeDetailsFragment : Fragment() {

    private var animeId = 0
    private var image: Bitmap? = null

    @Inject
    lateinit var animeDetailsVMFactory: AnimeDetailsVMFactory
    private lateinit var animeDetailsViewModel: AnimeDetailsViewModel

    companion object {
        private const val ARG_ANIME_ID = "anime_id"
        private const val ARG_IMAGE = "image"

        @JvmStatic
        fun newInstance(animeId: Int, image: Bitmap?) =
            AnimeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ANIME_ID, animeId)
                    putParcelable(ARG_IMAGE, image)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animeId = it.getInt(ARG_ANIME_ID)
            image = it.getParcelable(ARG_IMAGE)
        }

        // inject
        (activity!!.application as AniSearchApp).getAnimeDetailsComponent().inject(this)

        // view model
        animeDetailsViewModel = ViewModelProviders.of(this, animeDetailsVMFactory).get(AnimeDetailsViewModel::class.java)
        animeDetailsViewModel.getAnimeDetailsLiveData().observe(this, Observer {
            val animeDetails = it ?: return@Observer
            f_anime_details_t_name_english.text = animeDetails.title?.english ?: ""

            f_anime_details_t_format.text =  animeDetails.format ?: ""
            f_anime_details_t_status.text = animeDetails.status ?: ""
            f_anime_details_t_description.text = animeDetails.description ?: ""
            f_anime_details_t_start_date.text = animeDetails.startDate?.toString() ?: ""
            f_anime_details_t_end_date.text = animeDetails.endDate?.toString() ?: ""
            f_anime_details_t_episodes.text = animeDetails.episodes?.toString() ?: ""
            val durationL = animeDetails.duration
            f_anime_details_t_duration.text = if (durationL != null) durationL.toString() + "minutes" else ""

            animeDetails.genres?.forEachIndexed { index, genre ->
                val itemView = View.inflate(context, R.layout.item_anime_details_genre, null)
                itemView.i_anime_details_t_genre.text = genre
                f_anime_details_gl_genres.addView(itemView)
                (itemView.layoutParams as GridLayout.LayoutParams).columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_anime_details_i_image.setImageBitmap(image)

        animeDetailsViewModel.getErrorLiveData().observe(this, Observer {
            Toast.makeText(context, it!!.message, Toast.LENGTH_LONG).show()
        })

        animeDetailsViewModel.getAnimeDetails(animeId)

    }
}
