package com.example.israel.anisearch.fragment


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.israel.anisearch.R
import com.example.israel.anisearch.activity.SearchActivity
import com.example.israel.anisearch.adapter.TopListAdapter
import com.example.israel.anisearch.statics.AniListType
import com.example.israel.anisearch.app.AniSearchApp
import com.example.israel.anisearch.view_model.TopViewModel
import com.example.israel.anisearch.view_model.factory.TopVMFactory
import kotlinx.android.synthetic.main.fragment_top_list.*
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

        // adapter
        topListAdapter = TopListAdapter(object: TopListAdapter.OnItemClickedListener {
            override fun onItemClicked(v: View, imageView: ImageView, aniListType: AniListType, topId: Int, image: Bitmap?) {
                val fragment: Fragment = when (aniListType) {
                    AniListType.ANIME -> AnimeDetailsFragment.newInstance(topId, image)
                    AniListType.MANGA -> TODO()
                    AniListType.CHARACTER -> TODO()
                    AniListType.STAFF -> TODO()
                }

                isReplaced = true
                if (image == null) {
                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.f_top_list_fl_root, fragment)
                        .addToBackStack(null)
                        .commit()
                } else { // do shared transition
                    fragment.sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.f_top_list_fl_root, fragment)
                        .addToBackStack(null)
                        .addSharedElement(imageView, imageView.transitionName)
                        .commit()
                }
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

        // error
        topViewModel.errorLiveData.observe(this, Observer {
            // TODO
        })

        // top view model
        topViewModel.topListViewState.observe(this, Observer {
            if (it.isLoading) {
                f_top_list_requesting.visibility = View.VISIBLE
            } else {
                f_top_list_requesting.visibility = View.GONE

                it.topList?.let {topList ->
                    handleTopList(it.aniListType, topList)
                }
            }
        })

        // setup recycler view
        f_top_list_r.setHasFixedSize(true)
        f_top_list_r.layoutManager = LinearLayoutManager(
            context!!,
            RecyclerView.VERTICAL,
            false
        )
        f_top_list_r.adapter = topListAdapter

        // spinner
        f_top_list_s_type.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isReplaced) {
                    isReplaced = false
                    return
                }

                when (AniListType.fromStringArrPosition(f_top_list_s_type.selectedItemPosition)) {
                    AniListType.ANIME -> topViewModel.topAnimeListSelected(1, PER_PAGE)
                    AniListType.MANGA -> topViewModel.topMangaListSelected(1, PER_PAGE)
                    AniListType.CHARACTER -> topViewModel.topCharacterListSelected(1, PER_PAGE)
                    AniListType.STAFF -> topViewModel.topStaffListSelected(1, PER_PAGE)
                }

                f_top_list_requesting.visibility = View.VISIBLE
            }
        }

        // add types to spinner
        ArrayAdapter.createFromResource(context!!, R.array.search_types, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            f_top_list_s_type!!.adapter = it
        }

        // start search activity button
        f_top_list_b_start_search.setOnClickListener {
            // start search activity
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleTopList(aniListType: AniListType, topList: MutableList<Any>) {
        // can never be empty because this is TOP. Unless there's an error
        topListAdapter.setTopList(aniListType, topList)
    }
}
