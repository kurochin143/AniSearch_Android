package com.example.israel.anisearch.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.view_model.TopViewModel
import com.example.israel.anisearch.view_model.factory.TopVMFactory
import javax.inject.Inject

class TopListActivity : AppCompatActivity() {

    companion object {
        private const val PER_PAGE = 50
    }

    private var requestingTopConstraintLayout: ConstraintLayout? = null
    private var topTypesSpinner: Spinner? = null
    private lateinit var topListAdapter: TopListAdapter

    @Inject
    lateinit var topVMFactory: TopVMFactory

    private lateinit var topViewModel: TopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_list)

        // setup recycler view
        val topListRecyclerView = findViewById<RecyclerView>(R.id.top_list_recycler)
        topListRecyclerView.setHasFixedSize(true)

        topListRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        topListAdapter = TopListAdapter()
        topListRecyclerView.adapter = topListAdapter

        // top view model
        (application as AniSearchApp).getTopComponent().inject(this)
        topViewModel = ViewModelProviders.of(this, topVMFactory).get(TopViewModel::class.java)

        topViewModel.getTopListLiveData().observe(this, Observer {
            if (it != null) {
                topListAdapter.setTopList(it.topList)
            }

            requestingTopConstraintLayout!!.visibility = View.GONE
        })

        requestingTopConstraintLayout = findViewById(R.id.activity_home_constraint_requesting_top)

        // spinner
        topTypesSpinner = findViewById(R.id.activity_home_spinner_top_types)
        topTypesSpinner!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topListAdapter!!.setTopList(ArrayList())

                when (position) {
                    0 -> { // anime
                        topViewModel.getTopAnime(1, PER_PAGE)
                    }
                    1 -> { // manga
                        topViewModel.getTopManga(1, PER_PAGE)
                    }
                    2 -> { // character
                        topViewModel.getTopCharacters(1, PER_PAGE)
                    }
                    3 -> { // staff

                    }
                }

                requestingTopConstraintLayout!!.visibility = View.VISIBLE
            }
        }

        // add types to spinner
        ArrayAdapter.createFromResource(this, R.array.top_types, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            topTypesSpinner!!.adapter = it
        }

        // request anime
        topViewModel.getTopAnime(1, PER_PAGE)

        // start search activity button
        var searchButton = findViewById<FloatingActionButton>(R.id.activity_home_button_search)
        searchButton.setOnClickListener {
            // start search activity
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

    }


}
