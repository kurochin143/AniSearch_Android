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
import com.example.israel.anisearch.jikan_api.Jikan
import com.example.israel.anisearch.network.NetworkStatics
import okhttp3.*
import java.io.BufferedInputStream
import java.io.IOException

class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    private var searchResults: ArrayList<Jikan> = ArrayList()
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

        viewHolder.titleTextView.text = searchResult.title?: "no_title"

        if (searchResult.imageUrl != null) {
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

                    NetworkStatics.requestImage(searchResult.imageUrl!!).enqueue(object: Callback{
                        override fun onFailure(call: Call, e: IOException) {
                            e.printStackTrace()
                            onRequestImageFinished(uiHandler, positionT, null, call,null)
                        }

                        override fun onResponse(call: Call, response: Response) {
                            onRequestImageFinished(uiHandler, positionT, searchResult.imageUrl, call, response)
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

    private fun onRequestImageFinished(uiHandler: Handler, position: Int, url: String?, call: Call, response: Response?) {
        if (call.isCanceled) {
            return
        }

        if (response != null && response.isSuccessful && response.body() != null) {
            val bufferedInputStream = BufferedInputStream(response.body()!!.byteStream())
            val image = BitmapFactory.decodeStream(bufferedInputStream)
            bufferedInputStream.close()

            // do not cache if there is no image
            if (image != null) {
                uiHandler.post {
                    isRequestingImages[position] = false

                    imageCaches[position] = Pair(url, image)

                    // TODO what I really wanna do is directly set the image of the view holder if it exists. There's no way to get it

                    notifyItemChanged(position)
                }

                return // successful
            }
        }

        // failed
        uiHandler.post {
            isRequestingImages[position] = false

            // @NOTE: this will request a download again
            notifyItemChanged(position)
        }
    }

    fun setSearchResults(searchResults: ArrayList<Jikan>) {
        this.searchResults = searchResults
        Array<Pair<String?, Bitmap?>?>(searchResults.size) {null}.toCollection(imageCaches)
        Array(searchResults.size) {false}.toCollection(isRequestingImages)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_search_result_text_title)
        val imageImageView: ImageView = itemView.findViewById(R.id.item_search_result_image_image)
        val requestingImageProgressBar: ProgressBar = itemView.findViewById(R.id.item_search_result_progress_bar_requesting_image)
    }
}
