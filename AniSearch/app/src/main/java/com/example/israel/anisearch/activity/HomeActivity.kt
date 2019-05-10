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
import com.example.israel.anisearch.jikan_api.JikanApiDao
import com.example.israel.anisearch.jikan_api.Jikan
import com.example.israel.anisearch.jikan_api.JikanList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val SPAN_COUNT = 4
    }

    private var requestingTopConstraintLayout: ConstraintLayout? = null
    private var topTypesSpinner: Spinner? = null
    private var topListAdapter: TopListAdapter? = null
    private var getTopCall: Call<JikanList<Jikan>?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        requestingTopConstraintLayout = findViewById(R.id.activity_home_constraint_requesting_top)
        topTypesSpinner = findViewById(R.id.activity_home_spinner_top_types)
        topTypesSpinner!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                requestTop()
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
        val topListRecyclerView = findViewById<RecyclerView>(R.id.recycler_top_list)
        topListRecyclerView.setHasFixedSize(true)

        topListRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        topListAdapter = TopListAdapter()
        topListRecyclerView.adapter = topListAdapter

        requestTop()

    }

    private fun requestTop() {
        if (getTopCall != null) {
            return
        }

        requestingTopConstraintLayout!!.visibility = View.VISIBLE
        topListAdapter!!.setTopList(ArrayList())

        getTopCall = JikanApiDao.apiService.getTop((topTypesSpinner!!.selectedItem as String).toLowerCase(), 1)
        getTopCall!!.enqueue(object: Callback<JikanList<Jikan>?> {
            override fun onFailure(call: Call<JikanList<Jikan>?>, t: Throwable) {
                onGetTopCallFinished(null)
            }

            override fun onResponse(
                call: Call<JikanList<Jikan>?>,
                response: Response<JikanList<Jikan>?>
            ) {
                onGetTopCallFinished(response)
            }

        })
    }

    private fun onGetTopCallFinished(response: Response<JikanList<Jikan>?>?) {
        requestingTopConstraintLayout!!.visibility = View.GONE

        if (getTopCall!!.isCanceled) {
            return
        }

        getTopCall = null

        if (response != null && response.isSuccessful && response.body() != null && response.body()!!.list != null) {
            topListAdapter!!.setTopList(response.body()!!.list!!)
        }

    }



}
