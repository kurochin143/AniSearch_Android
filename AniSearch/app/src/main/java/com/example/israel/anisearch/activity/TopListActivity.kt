package com.example.israel.anisearch.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.example.israel.anisearch.R
import com.example.israel.anisearch.fragment.TopListFragment

class TopListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_list)

        // Glide memory high
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH)

        supportFragmentManager.beginTransaction()
            .add(R.id.f_top_list_fl_root, TopListFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Glide memory normal
        Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL)
    }
}