package com.example.israel.anisearch.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.israel.anisearch.R
import com.example.israel.anisearch.fragment.SearchResultsFragment
import com.example.israel.anisearch.jikan_api.JikanMedia
import com.example.israel.anisearch.view.SearchOptionsViewHolder
import kotlinx.android.synthetic.main.activity_search.*
import java.lang.StringBuilder

class SearchActivity : AppCompatActivity() {

    private lateinit var ratedSearchOptionsViewHolder: SearchOptionsViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        ratedSearchOptionsViewHolder = SearchOptionsViewHolder(findViewById(R.id.activity_search_linear_rated_list), "All", Color.GREEN)
        ratedSearchOptionsViewHolder.addOption(JikanMedia.RATED_RX, Color.GREEN)

        val searchButton = findViewById<Button>(R.id.activity_search_button_search)
        searchButton.setOnClickListener {
            val searchResultsFragment = SearchResultsFragment.newInstance(a_search_et_query.text.toString())
            this@SearchActivity.supportFragmentManager.beginTransaction()
                .add(R.id.a_search_fl_root, searchResultsFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
