package com.example.israel.anisearch.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.israel.anisearch.R
import com.example.israel.anisearch.adapter.TopListAdapter
import com.example.israel.anisearch.anilist_api.AniListApiDao
import com.example.israel.anisearch.anilist_api.AniListType
import com.example.israel.anisearch.anilist_api.AnimeSearchResult
import com.example.israel.anisearch.anilist_api.MangaSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private var requestingTopConstraintLayout: ConstraintLayout? = null
    private var topTypesSpinner: Spinner? = null
    private var topListAdapter: TopListAdapter? = null
    private var getTopAnimeCall: Call<AnimeSearchResult?>? = null
    private var getTopMangaCall: Call<MangaSearchResult?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        requestingTopConstraintLayout = findViewById(R.id.activity_home_constraint_requesting_top)
        topTypesSpinner = findViewById(R.id.activity_home_spinner_top_types)
        topTypesSpinner!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> { // anime
                        requestTopAnime()
                    }
                    1 -> { // manga
                        requestTopManga()
                    }
                    2 -> { // character

                    }
                    3 -> { // staff

                    }
                }
            }
        }

        ArrayAdapter.createFromResource(this, R.array.top_types, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            topTypesSpinner!!.adapter = it
        }

        // start search activity button
        var searchButton = findViewById<FloatingActionButton>(R.id.activity_home_button_search)
        searchButton.setOnClickListener {
            // start search activity
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        // setup recycler view
        val topListRecyclerView = findViewById<RecyclerView>(R.id.top_list_recycler)
        topListRecyclerView.setHasFixedSize(true)

        topListRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        topListAdapter = TopListAdapter()
        topListRecyclerView.adapter = topListAdapter

        requestTopAnime()

    }

    private fun requestTopAnime() {
        if (getTopAnimeCall != null) {
            return
        }

        requestingTopConstraintLayout!!.visibility = View.VISIBLE
        topListAdapter!!.setTopList(AniListType.ANIME, ArrayList())

        getTopAnimeCall = AniListApiDao.getTopAnime( 1, 50)
        getTopAnimeCall!!.enqueue(object: Callback<AnimeSearchResult?> {
            override fun onFailure(call: Call<AnimeSearchResult?>, t: Throwable) {
                onGetTopAnimeCallFinished(null)
            }

            override fun onResponse(
                call: Call<AnimeSearchResult?>,
                response: Response<AnimeSearchResult?>
            ) {
                onGetTopAnimeCallFinished(response)
            }

        })
    }

    private fun onGetTopAnimeCallFinished(response: Response<AnimeSearchResult?>?) {
        requestingTopConstraintLayout!!.visibility = View.GONE

        if (getTopAnimeCall!!.isCanceled) {
            return
        }

        getTopAnimeCall = null

        if (response != null && response.isSuccessful &&
            response.body() != null &&
            response.body()!!.data != null &&
            response.body()!!.data!!.page != null &&
            response.body()!!.data!!.page!!.media != null) {
            val topAnimeList = response.body()!!.data!!.page!!.media
            topListAdapter!!.setTopList(AniListType.ANIME, topAnimeList as ArrayList<Any>)
        }

    }

    private fun requestTopManga() {
        if (getTopMangaCall != null) {
            return
        }

        requestingTopConstraintLayout!!.visibility = View.VISIBLE
        topListAdapter!!.setTopList(AniListType.MANGA, ArrayList())

        getTopMangaCall = AniListApiDao.getTopManga(1, 50)
        getTopMangaCall!!.enqueue(object: Callback<MangaSearchResult?> {
            override fun onFailure(call: Call<MangaSearchResult?>, t: Throwable) {
                onGetTopMangaCallFinished(null)
            }

            override fun onResponse(
                call: Call<MangaSearchResult?>,
                response: Response<MangaSearchResult?>
            ) {
                onGetTopMangaCallFinished(response)
            }

        })
    }

    private fun onGetTopMangaCallFinished(response: Response<MangaSearchResult?>?) {
        requestingTopConstraintLayout!!.visibility = View.GONE

        if (getTopMangaCall!!.isCanceled) {
            return
        }

        getTopMangaCall = null

        if (response != null && response.isSuccessful &&
            response.body() != null &&
            response.body()!!.data != null &&
            response.body()!!.data!!.page != null &&
            response.body()!!.data!!.page!!.media != null) {
            val topMangaList = response.body()!!.data!!.page!!.media
            topListAdapter!!.setTopList(AniListType.MANGA, topMangaList as ArrayList<Any>)
        }
    }



}
