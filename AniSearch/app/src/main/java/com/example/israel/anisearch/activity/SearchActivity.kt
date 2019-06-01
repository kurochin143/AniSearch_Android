package com.example.israel.anisearch.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.israel.anisearch.R
import com.example.israel.anisearch.fragment.SearchResultsFragment
import com.example.israel.anisearch.jikan_api.JikanMedia
import com.example.israel.anisearch.view.SearchOptionsViewHolder
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        a_search_b_search.setOnClickListener {
            val searchResultsFragment = SearchResultsFragment.newInstance(a_search_et_query.text.toString())
            this@SearchActivity.supportFragmentManager.beginTransaction()
                .add(R.id.a_search_fl_root, searchResultsFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
