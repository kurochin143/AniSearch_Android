package com.example.israel.anisearch.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.example.israel.anisearch.R
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.fragment.SearchResultsFragment
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        ArrayAdapter.createFromResource(this, R.array.search_types, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            a_search_s_type.adapter = it
        }

        a_search_b_search.setOnClickListener {
            val type = AniListType.fromStringArrPosition(a_search_s_type.selectedItemPosition)
            val searchResultsFragment = SearchResultsFragment.newInstance(a_search_et_query.text.toString(), type)
            this@SearchActivity.supportFragmentManager.beginTransaction()
                .add(R.id.a_search_fl_root, searchResultsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()

        // Glide memory high
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH)
    }

    override fun onPause() {
        super.onPause()

        // Glide memory normal
        Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL)
    }
}
