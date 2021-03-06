package com.example.israel.anisearch.fragment


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.israel.anisearch.R
import com.example.israel.anisearch.adapter.SearchResultsAdapter
import com.example.israel.anisearch.statics.AniListType
import com.example.israel.anisearch.statics.CharacterSearchSort
import com.example.israel.anisearch.statics.MediaSearchSort
import com.example.israel.anisearch.statics.StaffSearchSort
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.model.presentation.SearchResult
import com.example.israel.anisearch.view_model.SearchViewModel
import com.example.israel.anisearch.view_model.factory.SearchVMFactory
import kotlinx.android.synthetic.main.fragment_search_results.*
import javax.inject.Inject

class SearchResultsFragment : Fragment() {

    private var isReplaced: Boolean = false

    private lateinit var query: String
    private lateinit var aniListType: AniListType
    private var searchResultsAdapter: SearchResultsAdapter? = null

    @Inject
    lateinit var searchResultVMFactory: SearchVMFactory

    private lateinit var searchViewModel: SearchViewModel

    companion object {
        private const val SPAN_COUNT = 3
        private const val ARG_QUERY = "query"
        private const val ARG_ANI_LIST_TYPE = "ani_list_type"
        private const val PER_PAGE: Int = 50

        fun newInstance(query: String, aniListType: AniListType) =
            SearchResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                    putSerializable(ARG_ANI_LIST_TYPE, aniListType)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY)!!
            aniListType = it.getSerializable(ARG_ANI_LIST_TYPE)!! as AniListType
        }

        // inject
        (activity!!.application as AniSearchApp).getSearchComponent().inject(this)

        // adapter
        searchResultsAdapter = SearchResultsAdapter(object: SearchResultsAdapter.OnLoadNextPageListener {
            override fun loadNextPage(page: Int) {
                search(page)
            }
        }, object: SearchResultsAdapter.OnItemClickedListener {
            override fun onItemClicked(v: View, imageView: ImageView, aniListType: AniListType, searchResultId: Int, image: Bitmap?) {
                val fragment: Fragment = when (aniListType) {
                    AniListType.ANIME -> AnimeDetailsFragment.newInstance(searchResultId, image)
                    AniListType.MANGA -> TODO()
                    AniListType.CHARACTER -> TODO()
                    AniListType.STAFF -> TODO()
                }

                isReplaced = true
                if (image == null) {
                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.a_search_fl_root, fragment)
                        .addToBackStack(null)
                        .commit()
                } else { // do shared transition
                    fragment.sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.a_search_fl_root, fragment)
                        .addToBackStack(null)
                        .addSharedElement(imageView, imageView.transitionName)
                        .commit()
                }
            }
        })

        // view model
        searchViewModel = ViewModelProviders.of(this, searchResultVMFactory).get(SearchViewModel::class.java)
        searchViewModel.getSearchResultsLiveData().observe(this, Observer {
            f_search_results_pb_requesting.visibility = View.GONE
            if (it == null) return@Observer

            if (it.currentPage == 1) {
                searchResultsAdapter!!.setSearchResults(it)
            } else {
                searchResultsAdapter!!.addSearchResults(it)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recycler view
        f_search_results_r.setHasFixedSize(true)
        f_search_results_r.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, SPAN_COUNT)
        f_search_results_r.adapter = searchResultsAdapter

        if (isReplaced) {
            isReplaced = false
        } else {
            // page 1 search
            search(1)
        }
    }

    private fun search(page: Int) {
        when (aniListType) {
            AniListType.ANIME -> searchViewModel.searchAnime(page, PER_PAGE, query, MediaSearchSort.SEARCH_MATCH)
            AniListType.MANGA -> searchViewModel.searchManga(page, PER_PAGE, query, MediaSearchSort.SEARCH_MATCH)
            AniListType.CHARACTER -> searchViewModel.searchCharacter(page, PER_PAGE, query, CharacterSearchSort.SEARCH_MATCH)
            AniListType.STAFF -> searchViewModel.searchStaff(page, PER_PAGE, query, StaffSearchSort.SEARCH_MATCH)
        }
    }

}
