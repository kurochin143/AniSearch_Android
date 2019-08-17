package com.example.israel.anisearch.fragment


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.israel.anisearch.R
import com.example.israel.anisearch.adapter.AnimeDetailsPagerAdapter
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.view_model.AnimeDetailsViewModel
import com.example.israel.anisearch.view_model.factory.AnimeDetailsVMFactory
import kotlinx.android.synthetic.main.fragment_anime_details.*
import javax.inject.Inject

class AnimeDetailsFragment : androidx.fragment.app.Fragment() {

    private var animeId = 0
    private var image: Bitmap? = null

    private lateinit var animeDetailsPagerAdapter: AnimeDetailsPagerAdapter

    @Inject
    lateinit var animeDetailsVMFactory: AnimeDetailsVMFactory
    private lateinit var animeDetailsViewModel: AnimeDetailsViewModel

    companion object {
        private const val ARG_ANIME_ID = "anime_id"
        private const val ARG_IMAGE = "image"

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
        animeDetailsViewModel.getErrorLiveData().observe(this, Observer {
            Toast.makeText(context, it!!.message, Toast.LENGTH_LONG).show()
        })
        animeDetailsViewModel.getAnimeDetailsLiveData().observe(this, Observer {
            val animeDetails = it ?: return@Observer // TODO make Anime nonnull

            f_anime_details_pb.visibility = View.INVISIBLE
            animeDetailsPagerAdapter = AnimeDetailsPagerAdapter(childFragmentManager, animeDetails, image)
            f_anime_details_vp.adapter = animeDetailsPagerAdapter
            f_anime_details_tl.setupWithViewPager(f_anime_details_vp)
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

        animeDetailsViewModel.getAnimeDetails(animeId)

    }
}
