package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.israel.anisearch.R
import com.example.israel.anisearch.anilist_api.statics.AniListType
import com.example.israel.anisearch.model.SearchResult
import com.example.israel.anisearch.model.SearchResults
import kotlinx.android.synthetic.main.item_search_result.view.*
import kotlinx.android.synthetic.main.item_search_result_load_more.view.*

class SearchResultsAdapter(
    private var onLoadNextPageListener: OnLoadNextPageListener,
    private var onItemClickedListener: OnItemClickedListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_CONTENT = 0
        private const val TYPE_LOAD_MORE = 1
    }

    private var searchResults: SearchResults = SearchResults(AniListType.ANIME, ArrayList(), 0, 0)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return when(p1) {
            TYPE_CONTENT -> ContentViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_search_result, p0, false))
            else -> LoadMoreViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_search_result_load_more, p0, false))
        }
    }

    override fun getItemCount(): Int {
        return searchResults.searchResults.size + 1
    }

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ContentViewHolder) {
            val searchResult = searchResults.searchResults[position]
            viewHolder.bind(searchResult, onItemClickedListener)

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

    class ContentViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bind(searchResult: SearchResult, onItemClickedListener: OnItemClickedListener) {
            itemView.i_search_result_t_name.text = searchResult.name

            if (searchResult.imageUrl != null) {
                itemView.i_search_result_pb_requesting_image.visibility = View.VISIBLE

                Glide
                    .with(itemView.context)
                    .load(searchResult.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .addListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_search_result_pb_requesting_image.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_search_result_pb_requesting_image.visibility = View.GONE
                            return false
                        }

                    })
                    .into(itemView.i_search_result_i_image)

            } else { // no image url
                itemView.i_search_result_pb_requesting_image.visibility = View.GONE
                itemView.i_search_result_i_image.setImageBitmap(null)
            }

            itemView.i_search_result_i_image.transitionName = "search_result_image_transition" + searchResult.id.toString()

            itemView.setOnClickListener {
                val drawable = itemView.i_search_result_i_image.drawable
                val image: Bitmap?
                if (drawable is BitmapDrawable) {
                    image = drawable.bitmap
                } else {
                    image = null
                }

                onItemClickedListener.onItemClicked(it, itemView.i_search_result_i_image, searchResult, image)
            }
        }

    }

    class LoadMoreViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    interface OnLoadNextPageListener {
        fun loadNextPage(page: Int)
    }

    interface OnItemClickedListener {
        fun onItemClicked(v: View, imageView: ImageView, searchResult: SearchResult, image: Bitmap?)
    }
}
