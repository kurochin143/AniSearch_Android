package com.example.israel.anisearch.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView

import com.example.israel.anisearch.R
import com.example.israel.anisearch.activity.SearchActivity
import com.example.israel.anisearch.adapter.TopListAdapter
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.model.Top
import com.example.israel.anisearch.view_model.TopViewModel
import com.example.israel.anisearch.view_model.factory.TopVMFactory
import kotlinx.android.synthetic.main.fragment_top_list.*
import kotlinx.android.synthetic.main.layout_top_list.*
import javax.inject.Inject

class TopListFragment : Fragment() {

    companion object {
        private const val PER_PAGE = 50
    }

    private lateinit var topListAdapter: TopListAdapter

    @Inject
    lateinit var topVMFactory: TopVMFactory

    private lateinit var topViewModel: TopViewModel

    private var isReplaced: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inject
        (activity!!.application as AniSearchApp).getTopComponent().inject(this)

        // top view model
        topViewModel = ViewModelProviders.of(this, topVMFactory).get(TopViewModel::class.java)
        topViewModel.getTopListLiveData().observe(this, Observer {
            a_top_list_cl_requesting.visibility = View.GONE

            if (it != null) {
                topListAdapter.setTopList(it.topList)
            }
        })

        // adapter
        topListAdapter = TopListAdapter(object: TopListAdapter.OnItemClickedListener {
            override fun onItemClicked(v: View, imageView: ImageView, top: Top, image: Bitmap?) {
                // TODO if image is valid do shared transition

                val fragment: Fragment = when (top.type) {
                    AniListType.ANIME -> AnimeDetailsFragment.newInstance(top.id, image, imageView.transitionName)
                    else -> return
                }

                fragment.sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

                isReplaced = true
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.a_top_list_fl_root, fragment)
                    .addToBackStack(null)
                    .addSharedElement(imageView, imageView.transitionName)
                    .commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup recycler view
        a_top_list_r.setHasFixedSize(true)
        a_top_list_r.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        a_top_list_r.adapter = topListAdapter

        // spinner
        a_top_list_s_type.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isReplaced) {
                    isReplaced = false
                    return
                }

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
        ArrayAdapter.createFromResource(context!!, R.array.search_types, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            a_top_list_s_type!!.adapter = it
        }

        // start search activity button
        a_top_list_b_start_search.setOnClickListener {
            // start search activity
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }

    }
}
