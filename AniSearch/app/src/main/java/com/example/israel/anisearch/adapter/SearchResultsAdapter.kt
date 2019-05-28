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
import com.example.israel.anisearch.anilist_api.Anime
import com.example.israel.anisearch.anilist_api.Manga
import com.example.israel.anisearch.network.NetworkStatics
import okhttp3.*
import java.io.BufferedInputStream
import java.io.IOException

class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    private var type = AniListType.ANIME
    private var searchResults: ArrayList<Any> = ArrayList()
    private var imageCaches: ArrayList<Pair<String?, Bitmap?>?> = ArrayList()
    private var isRequestingImages: ArrayList<Boolean> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_search_result, p0, false))
        return viewHolder
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val searchResult = searchResults[position]

        var imageUrl: String? = null
        when (type) {
            AniListType.ANIME -> {
                searchResult as Anime
                viewHolder.titleTextView.text = searchResult.title?.romaji?: "no_title"
                imageUrl = searchResult.coverImage?.large
            }
            AniListType.MANGA -> {
                searchResult as Manga
                viewHolder.titleTextView.text = searchResult.title?.romaji?: "no_title"
                imageUrl = searchResult.coverImage?.large
            }
        }

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
    }

    fun setSearchResults(type: String, searchResults: ArrayList<Any>) {
        this.type = type
        this.searchResults = searchResults
        imageCaches = ArrayList(this.searchResults.size)
        isRequestingImages = ArrayList(this.searchResults.size)
        repeat(this.searchResults.size) {
            imageCaches.add(null)
            isRequestingImages.add(false)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_search_result_text_title)
        val imageImageView: ImageView = itemView.findViewById(R.id.item_search_result_image_image)
        val requestingImageProgressBar: ProgressBar = itemView.findViewById(R.id.item_search_result_progress_bar_requesting_image)
    }
}
