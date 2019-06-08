package com.example.israel.anisearch.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.israel.anisearch.R
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.view_model.AnimeDetailsViewModel
import com.example.israel.anisearch.view_model.factory.AnimeDetailsVMFactory
import kotlinx.android.synthetic.main.fragment_anime_details.*
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

        (activity!!.application as AniSearchApp).getAnimeDetailsComponent().inject(this)
        animeDetailsViewModel = ViewModelProviders.of(this, animeDetailsVMFactory).get(AnimeDetailsViewModel::class.java)

        f_anime_details_i_image.setImageBitmap(image)

        animeDetailsViewModel.getAnimeDetailsLiveData().observe(this, Observer {
            val anime = it ?: return@Observer
            val title = anime.title?.english ?: "NO_TITLE"

            f_anime_details_t_name_english.text = title
        })

        animeDetailsViewModel.getAnimeDetails(animeId)

    }
}
