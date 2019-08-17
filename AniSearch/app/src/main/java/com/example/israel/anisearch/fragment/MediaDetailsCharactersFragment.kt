package com.example.israel.anisearch.fragment


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.israel.anisearch.R
import com.example.israel.anisearch.adapter.MediaDetailsCharactersAdapter
import com.example.israel.anisearch.statics.CharacterSearchSort
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.view_model.AnimeDetailsViewModel
import com.example.israel.anisearch.view_model.factory.AnimeDetailsVMFactory
import kotlinx.android.synthetic.main.fragment_media_details_characters.*
import javax.inject.Inject

class MediaDetailsCharactersFragment : androidx.fragment.app.Fragment() {

    private var isNew = true

    private var animeId: Int = -1

    private lateinit var mediaDetailsCharactersAdapter: MediaDetailsCharactersAdapter

    @Inject
    lateinit var animeDetailsVMFactory: AnimeDetailsVMFactory
    private lateinit var animeDetailsViewModel: AnimeDetailsViewModel

    companion object {
        private const val SPAN_COUNT = 3
        private const val PER_PAGE = 20
        private const val ARG_ANIME_ID = "anime_id"

        fun newInstance(animeId: Int) =
            MediaDetailsCharactersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ANIME_ID, animeId)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animeId = it.getInt(ARG_ANIME_ID)
        }

        mediaDetailsCharactersAdapter = MediaDetailsCharactersAdapter()

        // inject
        (activity!!.application as AniSearchApp).getAnimeDetailsComponent().inject(this)

        // view model
        animeDetailsViewModel = ViewModelProviders.of(this, animeDetailsVMFactory).get(AnimeDetailsViewModel::class.java)
        animeDetailsViewModel.getErrorLiveData().observe(this, Observer {
            Toast.makeText(context, it!!.message, Toast.LENGTH_LONG).show()
        })
        animeDetailsViewModel.getMediaCharactersLiveData().observe(this, Observer {
            val mediaCharacters = it ?: return@Observer
            val characterConnection = mediaCharacters.characterConnection ?: return@Observer
            characterConnection.edges ?: return@Observer
            characterConnection.pageInfo?.currentPage ?: return@Observer
            characterConnection.pageInfo?.lastPage ?: return@Observer

            mediaDetailsCharactersAdapter.setCharacterConnection(characterConnection)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_details_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_media_details_characters_r.setHasFixedSize(true)
        f_media_details_characters_r.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, SPAN_COUNT)
        f_media_details_characters_r.adapter = mediaDetailsCharactersAdapter

        if (isNew) {
            isNew = false
            animeDetailsViewModel.getMediaCharacters(animeId, 1, PER_PAGE, CharacterSearchSort.ROLE)
        }
    }
}
