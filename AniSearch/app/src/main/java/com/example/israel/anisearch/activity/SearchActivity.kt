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
import java.lang.StringBuilder

class SearchActivity : AppCompatActivity() {

    private lateinit var ratedSearchOptionsViewHolder: SearchOptionsViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        ratedSearchOptionsViewHolder = SearchOptionsViewHolder(findViewById(R.id.activity_search_linear_rated_list), "All", Color.GREEN)
        ratedSearchOptionsViewHolder.addOption(JikanMedia.RATED_RX, Color.GREEN)

        val queryEditText = findViewById<EditText>(R.id.activity_search_edit_text_search_anime_query)

        val searchButton = findViewById<Button>(R.id.activity_search_button_search)
        searchButton.setOnClickListener {
            val options = HashMap<String, String>()
            val ratedListStrB = StringBuilder()
            if (ratedSearchOptionsViewHolder.names.size == 1 && ratedSearchOptionsViewHolder.names[0] == "All") {
                // add all
                ratedListStrB.append(JikanMedia.RATED_ALL_STR)
            } else {
                for (name in ratedSearchOptionsViewHolder.names) {
                    ratedListStrB.append(name)
                    ratedListStrB.append(SearchResultsFragment.OPTION_LIST_DELIMITER)
                }
                ratedListStrB.setLength(ratedListStrB.length - 1)
            }
            options[SearchResultsFragment.OPTIONS_KEY_RATED_LIST_STR] = ratedListStrB.toString()

            val searchResultsFragment = SearchResultsFragment.newInstance(queryEditText.text.toString(), 1, options)
            this@SearchActivity.supportFragmentManager.beginTransaction()
                .add(R.id.activity_search_root, searchResultsFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
