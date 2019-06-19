package com.example.israel.anisearch.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.israel.anisearch.R
import com.example.israel.anisearch.adapter.SearchResultsAdapter
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.anilist_api.statics.CharacterSearchSort
import com.example.israel.anisearch.anilist_api.statics.MediaSearchSort
import com.example.israel.anisearch.anilist_api.statics.StaffSearchSort
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.view_model.SearchViewModel
import com.example.israel.anisearch.view_model.factory.SearchVMFactory
import kotlinx.android.synthetic.main.fragment_search_results.*
import javax.inject.Inject

class SearchResultsFragment : Fragment() {

    private lateinit var query: String
    private lateinit var type: String
    private var searchResultsAdapter: SearchResultsAdapter? = null

    @Inject
    lateinit var searchResultVMFactory: SearchVMFactory

    private lateinit var searchViewModel: SearchViewModel

    companion object {
        private const val SPAN_COUNT = 3
        private const val ARG_QUERY = "query"
        private const val ARG_TYPE = "type"
        private const val PER_PAGE: Int = 50

        fun newInstance(query: String, type: String) =
            SearchResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                    putString(ARG_TYPE, type)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY)!!
            type = it.getString(ARG_TYPE)!!
        }

        // inject
        (activity!!.application as AniSearchApp).getSearchComponent().inject(this)

        // adapter
        searchResultsAdapter = SearchResultsAdapter(object: SearchResultsAdapter.OnLoadNextPageListener {
            override fun loadNextPage(page: Int) {
                search(page)
            }
        })

        // view model
        searchViewModel = ViewModelProviders.of(this, searchResultVMFactory).get(SearchViewModel::class.java)
        searchViewModel.getSearchResultsLiveData().observe(this, Observer {
            f_search_results_pb_requesting.visibility = View.GONE

            if (it == null) {
                return@Observer
            }

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
        f_search_results_r.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        f_search_results_r.adapter = searchResultsAdapter

        // page 1 search
        search(1)
    }

    private fun search(page: Int) {
        when (type) {
            AniListType.ANIME -> searchViewModel.searchAnime(page, PER_PAGE, query, MediaSearchSort.SEARCH_MATCH)
            AniListType.MANGA -> searchViewModel.searchManga(page, PER_PAGE, query, MediaSearchSort.SEARCH_MATCH)
            AniListType.CHARACTER -> searchViewModel.searchCharacter(page, PER_PAGE, query, CharacterSearchSort.SEARCH_MATCH)
            AniListType.STAFF -> searchViewModel.searchStaff(page, PER_PAGE, query, StaffSearchSort.SEARCH_MATCH)
        }
    }

}
