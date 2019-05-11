package com.example.israel.anisearch.fragment


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
import com.example.israel.anisearch.anilist_api.AniListApiDao
import com.example.israel.anisearch.anilist_api.AniListType
import com.example.israel.anisearch.anilist_api.AnimeSearchResult
import com.example.israel.anisearch.jikan_api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsFragment : Fragment() {

    private var query: String = ""
    private var page: Int = 0
    private var options: HashMap<String, String> = HashMap()
    private var fragmentView: View? = null
    private var animeSearchCall: Call<AnimeSearchResult?>? = null
    private var requestingProgressBar: ProgressBar? = null
    private var searchResultsAdapter: SearchResultsAdapter? = null

    companion object {
        private const val SPAN_COUNT = 3
        private const val ARG_QUERY = "query"
        private const val ARG_PAGE = "page"
        private const val ARG_OPTIONS = "options"
        const val OPTIONS_KEY_RATED_LIST_STR = "rated_list_str"
        const val OPTION_LIST_DELIMITER = ','

        @JvmStatic
        fun newInstance(query: String, page: Int, options: HashMap<String, String>) =
            SearchResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                    putInt(ARG_PAGE, page)
                    putSerializable(ARG_OPTIONS, options)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY)!!
            page = it.getInt(ARG_PAGE)
            options = it.getSerializable(ARG_OPTIONS) as HashMap<String, String>
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
        recyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
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
        searchResultsAdapter!!.setSearchResults(AniListType.ANIME, ArrayList())

        animeSearchCall = AniListApiDao.searchAnime(page, 50, query)

        animeSearchCall!!.enqueue(object: Callback<AnimeSearchResult?>{
            override fun onFailure(call: Call<AnimeSearchResult?>, t: Throwable) {
                t.printStackTrace()
                onAnimeSearchCallFinished(null)
            }

            override fun onResponse(
                call: Call<AnimeSearchResult?>,
                response: Response<AnimeSearchResult?>
            ) {
                onAnimeSearchCallFinished(response)
            }
        })
    }

    fun onAnimeSearchCallFinished(response: Response<AnimeSearchResult?>?) {
        requestingProgressBar!!.visibility = View.INVISIBLE

        if (animeSearchCall!!.isCanceled) {
            animeSearchCall = null
            return
        }
        animeSearchCall = null

        if (response != null && response.isSuccessful &&
            response.body() != null &&
            response.body()!!.data != null &&
            response.body()!!.data!!.page != null && response.body()!!.data!!.page!!.media != null) {
            val animeList = response.body()!!.data!!.page!!.media
            searchResultsAdapter!!.setSearchResults(AniListType.ANIME, animeList as ArrayList<Any>)
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
