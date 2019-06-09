package com.example.israel.anisearch.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.example.israel.anisearch.R
import com.example.israel.anisearch.adapter.TopListAdapter
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.fragment.AnimeDetailsFragment
import com.example.israel.anisearch.model.Top
import com.example.israel.anisearch.view_model.TopViewModel
import com.example.israel.anisearch.view_model.factory.TopVMFactory
import kotlinx.android.synthetic.main.activity_top_list.*
import kotlinx.android.synthetic.main.layout_top_list.*
import javax.inject.Inject

class TopListActivity : AppCompatActivity() {

    companion object {
        private const val PER_PAGE = 50
    }

    private lateinit var topListAdapter: TopListAdapter

    @Inject
    lateinit var topVMFactory: TopVMFactory

    private lateinit var topViewModel: TopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_list)

        // Glide memory high
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH)

        // setup recycler view
        a_top_list_r.setHasFixedSize(true)

        a_top_list_r.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        topListAdapter = TopListAdapter(object: TopListAdapter.OnItemClickedListener {
            override fun onItemClicked(top: Top, image: Bitmap?) {
                // TODO if image is valid do shared transition

                val fragment: Fragment?
                when (top.type) {
                    AniListType.ANIME -> fragment = AnimeDetailsFragment.newInstance(top.id, image)
                    else -> return
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.a_top_list_fl_root, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        a_top_list_r.adapter = topListAdapter

        // inject
        (application as AniSearchApp).getTopComponent().inject(this)

        // top view model
        topViewModel = ViewModelProviders.of(this, topVMFactory).get(TopViewModel::class.java)

        topViewModel.getTopListLiveData().observe(this, Observer {
            a_top_list_cl_requesting.visibility = View.GONE

            if (it != null) {
                topListAdapter.setTopList(it.topList)
            }
        })

        // spinner
        a_top_list_s_type
        a_top_list_s_type.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topListAdapter.setTopList(ArrayList())

                when (AniListType.fromStringArrPosition(a_top_list_s_type.selectedItemPosition)) {
                    AniListType.ANIME -> topViewModel.getTopAnime(1, PER_PAGE)
                    AniListType.MANGA -> topViewModel.getTopManga(1, PER_PAGE)
                    AniListType.CHARACTER -> topViewModel.getTopCharacters(1, PER_PAGE)
                    AniListType.STAFF -> topViewModel.getTopStaffs(1, PER_PAGE)
                }

                a_top_list_cl_requesting.visibility = View.VISIBLE
            }
        }

        // add types to spinner
        ArrayAdapter.createFromResource(this, R.array.search_types, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            a_top_list_s_type!!.adapter = it
        }

        // request anime
        topViewModel.getTopAnime(1, PER_PAGE)

        // start search activity button
        a_top_list_b_start_search.setOnClickListener {
            // start search activity
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Glide memory normal
        Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL)
    }
}