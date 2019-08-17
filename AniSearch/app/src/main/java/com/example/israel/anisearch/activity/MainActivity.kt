package com.example.israel.anisearch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.israel.anisearch.R

// TODO if cannot connect to internet, use local cache if possible
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, TopListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
