package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.israel.anisearch.R
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.model.SearchResults
import com.example.israel.anisearch.network.NetworkStatics
import kotlinx.android.synthetic.main.item_search_result_load_more.view.*
import okhttp3.*
import java.io.BufferedInputStream
import java.io.IOException

class SearchResultsAdapter(private var onLoadNextPageListener: OnLoadNextPageListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_CONTENT = 0
        private const val TYPE_LOAD_MORE = 1
    }

    private var searchResults: SearchResults = SearchResults(AniListType.ANIME, ArrayList(), 0, 0)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when(p1) {
            TYPE_CONTENT -> ContentViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_search_result, p0, false))
            else -> LoadMoreViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_search_result_load_more, p0, false))
        }
    }

    override fun getItemCount(): Int {
        return searchResults.searchResults.size + 1
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ContentViewHolder) {
            val searchResult = searchResults.searchResults[position]

            viewHolder.titleTextView.text = searchResult.name

            if (searchResult.imageUrl != null) {
                viewHolder.requestingImageProgressBar.visibility = View.VISIBLE

                Glide
                    .with(viewHolder.itemView.context)
                    .load(searchResult.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .addListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            viewHolder.requestingImageProgressBar.visibility = View.INVISIBLE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            viewHolder.requestingImageProgressBar.visibility = View.INVISIBLE
                            return false
                        }

                    })
                    .into(viewHolder.imageImageView)

            } else { // no image url
                viewHolder.requestingImageProgressBar.visibility = View.INVISIBLE
                viewHolder.imageImageView.setImageBitmap(null)
            }

        } else if (viewHolder is LoadMoreViewHolder) {
            if (searchResults.currentPage == searchResults.lastPage) { // no next page
                viewHolder.itemView.visibility = View.INVISIBLE
            } else {
                viewHolder.itemView.visibility = View.VISIBLE
                viewHolder.itemView.i_search_result_load_more_b.setOnClickListener {
                    onLoadNextPageListener.loadNextPage(searchResults.currentPage + 1)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == searchResults.searchResults.size) TYPE_LOAD_MORE else TYPE_CONTENT
    }

    fun setSearchResults(searchResults: SearchResults) {
        this.searchResults = searchResults
        notifyDataSetChanged()
    }

    fun addSearchResults(searchResults: SearchResults) {
        val oldSize = this.searchResults.searchResults.size
        this.searchResults.currentPage = searchResults.currentPage
        this.searchResults.lastPage = searchResults.lastPage
        this.searchResults.searchResults.addAll(searchResults.searchResults)
        notifyItemRangeChanged(oldSize, searchResults.searchResults.size + 1)
    }

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_search_result_text_title)
        val imageImageView: ImageView = itemView.findViewById(R.id.item_search_result_image_image)
        val requestingImageProgressBar: ProgressBar = itemView.findViewById(R.id.item_search_result_progress_bar_requesting_image)
    }

    class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnLoadNextPageListener {
        fun loadNextPage(page: Int)
    }
}
