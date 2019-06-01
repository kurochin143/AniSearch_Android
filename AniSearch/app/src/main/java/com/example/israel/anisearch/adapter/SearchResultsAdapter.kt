package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.israel.anisearch.R
import com.example.israel.anisearch.anilist_api.AniListType
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
    private var imageCaches: ArrayList<Pair<String?, Bitmap?>?> = ArrayList()
    private var isRequestingImages: ArrayList<Boolean> = ArrayList()

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

            var imageUrl: String? = searchResult.imageUrl
            if (imageUrl != null) {
                val imageCache = imageCaches[viewHolder.adapterPosition]
                if (imageCache == null) { // image has not been cached yet

                    viewHolder.imageImageView.setImageBitmap(null)
                    viewHolder.requestingImageProgressBar.visibility = View.VISIBLE

                    if (!isRequestingImages[viewHolder.adapterPosition]) { // not already requesting
                        // download the image
                        isRequestingImages[viewHolder.adapterPosition] = true

                        val uiHandler = Handler(viewHolder.itemView.context.mainLooper)

                        // preserve value
                        val positionT = viewHolder.adapterPosition

                        val onRequestImageFinished = fun(call: Call, response: Response?) {
                            if (call.isCanceled) {
                                return
                            }

                            if (response != null && response.isSuccessful && response.body() != null) {
                                val bufferedInputStream = BufferedInputStream(response.body()!!.byteStream())
                                val downloadedImage = BitmapFactory.decodeStream(bufferedInputStream)
                                bufferedInputStream.close()

                                // do not cache if there is no image
                                if (downloadedImage != null) {
                                    uiHandler.post {
                                        isRequestingImages[positionT] = false

                                        imageCaches[positionT] = Pair(imageUrl, downloadedImage)

                                        // set the image directly
                                        if (viewHolder.adapterPosition == positionT) {
                                            viewHolder.requestingImageProgressBar.visibility = View.INVISIBLE
                                            viewHolder.imageImageView.setImageBitmap(downloadedImage)
                                        }

                                        //notifyItemChanged(positionT)
                                    }

                                    return // successful
                                }
                            }

                            // failed
                            uiHandler.post {
                                isRequestingImages[positionT] = false


                                if (viewHolder.adapterPosition == positionT) {
                                    // @NOTE: this will request a download again
                                    notifyItemChanged(positionT)
                                }
                            }
                        }

                        NetworkStatics.requestImage(imageUrl).enqueue(object: Callback{
                            override fun onFailure(call: Call, e: IOException) {
                                e.printStackTrace()
                                onRequestImageFinished(call, null)
                            }

                            override fun onResponse(call: Call, response: Response) {
                                onRequestImageFinished(call, response)
                            }
                        })
                    }

                } else { // has image cache
                    viewHolder.requestingImageProgressBar.visibility = View.INVISIBLE
                    viewHolder.imageImageView.setImageBitmap(imageCache.second)
                }

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
        imageCaches = ArrayList(this.searchResults.searchResults.size)
        isRequestingImages = ArrayList(this.searchResults.searchResults.size)
        repeat(this.searchResults.searchResults.size) {
            imageCaches.add(null)
            isRequestingImages.add(false)
        }
        notifyDataSetChanged()
    }

    fun addSearchResults(searchResults: SearchResults) {
        val oldSize = this.searchResults.searchResults.size
        this.searchResults.currentPage = searchResults.currentPage
        this.searchResults.lastPage = searchResults.lastPage
        this.searchResults.searchResults.addAll(searchResults.searchResults)
        imageCaches.ensureCapacity(imageCaches.size + searchResults.searchResults.size)
        isRequestingImages.ensureCapacity(isRequestingImages.size + searchResults.searchResults.size)
        repeat(this.searchResults.searchResults.size) {
            imageCaches.add(null)
            isRequestingImages.add(false)
        }
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
