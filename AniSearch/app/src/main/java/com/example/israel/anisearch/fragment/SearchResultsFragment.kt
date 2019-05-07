package com.example.israel.anisearch.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.israel.anisearch.R
import com.example.israel.anisearch.adapter.SearchResultsAdapter
import com.example.israel.anisearch.jikan_api.JikanApiDao
import com.example.israel.anisearch.jikan_api.Media
import com.example.israel.anisearch.jikan_api.MediaAnime
import com.example.israel.anisearch.jikan_api.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_QUERY = "param1"
private const val ARG_PAGE = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SearchResultsFragment : Fragment() {

    private var query: String = ""
    private var page: Int = 0
    private var fragmentView: View? = null
    private var animeSearchCall: Call<SearchResult<MediaAnime>?>? = null
    private var requestingProgressBar: ProgressBar? = null
    private var searchResultsAdapter: SearchResultsAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(query: String, page: Int) =
            SearchResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                    putInt(ARG_PAGE, page)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY)!!
            page = it.getInt(ARG_PAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_search_results, container, false)

        // progress bar
        requestingProgressBar = fragmentView!!.findViewById(R.id.fragment_search_results_requesting)

        // recycler view
        val recyclerView = fragmentView!!.findViewById<RecyclerView>(R.id.fragment_search_results_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        searchResultsAdapter = SearchResultsAdapter()
        recyclerView.adapter = searchResultsAdapter

        requestSearchAnime()

        return fragmentView
    }

    private fun requestSearchAnime() {
        if (animeSearchCall != null) {
            return
        }

        requestingProgressBar!!.visibility = View.VISIBLE
        searchResultsAdapter!!.setSearchResults(arrayOf())

        animeSearchCall = JikanApiDao.searchAnime(
            query, page, null, null,
            null, null, null,
            null, null
        )

        animeSearchCall!!.enqueue(object: Callback<SearchResult<MediaAnime>?>{
            override fun onFailure(call: Call<SearchResult<MediaAnime>?>, t: Throwable) {
                onAnimeSearchCallFinished(null)
            }

            override fun onResponse(
                call: Call<SearchResult<MediaAnime>?>,
                response: Response<SearchResult<MediaAnime>?>
            ) {
                onAnimeSearchCallFinished(response)
            }

        })
    }

    fun onAnimeSearchCallFinished(response: Response<SearchResult<MediaAnime>?>?) {
        requestingProgressBar!!.visibility = View.INVISIBLE

        if (animeSearchCall!!.isCanceled) {
            animeSearchCall = null
            return
        }
        animeSearchCall = null

        if (response != null && response.isSuccessful && response.body() != null) {

            val tn = Thread.currentThread().name
            searchResultsAdapter!!.setSearchResults(response.body()!!.results as Array<Media>)

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (animeSearchCall != null) {
            animeSearchCall!!.cancel()
            animeSearchCall = null
        }
    }

}
