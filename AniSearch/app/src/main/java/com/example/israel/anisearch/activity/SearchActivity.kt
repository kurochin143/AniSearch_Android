package com.example.israel.anisearch.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.israel.anisearch.R
import com.example.israel.anisearch.fragment.SearchResultsFragment

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val queryEditText = findViewById<EditText>(R.id.activity_search_edit_text_search_anime_query)

        val searchButton = findViewById<Button>(R.id.activity_search_button_search)
        searchButton.setOnClickListener {
            val searchResultsFragment = SearchResultsFragment.newInstance(queryEditText.text.toString(), 1)
            this@SearchActivity.supportFragmentManager.beginTransaction()
                .add(R.id.activity_search_root, searchResultsFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
