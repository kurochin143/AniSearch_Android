package com.example.israel.anisearch.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.israel.anisearch.R
import com.example.israel.anisearch.adapter.SearchResultsAdapter
import com.example.israel.anisearch.anilist_api.MediaSearchSort
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.view_model.SearchViewModel
import com.example.israel.anisearch.view_model.factory.SearchVMFactory
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search_results.*
import javax.inject.Inject

class SearchResultsFragment : Fragment() {

    private var query: String = ""
    private var searchResultsAdapter: SearchResultsAdapter? = null

    @Inject
    lateinit var searchResultVMFactory: SearchVMFactory

    lateinit var searchViewModel: SearchViewModel

    companion object {
        private const val SPAN_COUNT = 3
        private const val ARG_QUERY = "query"
        private const val PER_PAGE: Int = 50

        fun newInstance(query: String) =
            SearchResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY)!!
        }
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

        (activity!!.application as AniSearchApp).getSearchComponent().inject(this)
        searchViewModel = ViewModelProviders.of(this, searchResultVMFactory).get(SearchViewModel::class.java)

        // recycler view
        f_search_results_r.setHasFixedSize(true)
        f_search_results_r.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        searchResultsAdapter = SearchResultsAdapter(object: SearchResultsAdapter.OnLoadNextPageListener {
            override fun loadNextPage(page: Int) {
                searchViewModel.searchAnime(page, PER_PAGE, query)
            }
        })
        f_search_results_r.adapter = searchResultsAdapter

        // live data
        searchViewModel.getSearchResultsLiveData().observe(this, Observer {
            if (it == null) {
                return@Observer
            }

            if (it.currentPage == 1) {
                searchResultsAdapter!!.setSearchResults(it)
            } else {
                searchResultsAdapter!!.addSearchResults(it)
            }

            f_search_results_pb_requesting.visibility = View.GONE
        })

        // search anime
        searchViewModel.searchAnime(1, PER_PAGE, query, MediaSearchSort.SEARCH_MATCH)
    }

}
