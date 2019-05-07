package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.israel.anisearch.R
import com.example.israel.anisearch.jikan_api.Media
import com.example.israel.anisearch.network.NetworkStatics
import okhttp3.*
import java.io.IOException

class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    private var searchResults: Array<Media>? = arrayOf()
    private var imageCaches: Array<Pair<String?, Bitmap?>?>? = Array(0) {null}

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_search_result, p0, false))
    }

    override fun getItemCount(): Int {
        return searchResults!!.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val searchResult = searchResults!![position]

        viewHolder.titleTextView.text = searchResult.title

        if (searchResult.image_url != null) {

            val imageCache = imageCaches!![viewHolder.adapterPosition]
            if (imageCache == null) { // image has not been cached yet
                // download the image
                val handler = Handler(viewHolder.itemView.context.mainLooper)
                val positionT = viewHolder.adapterPosition
                NetworkStatics.requestImage(searchResult.image_url!!).enqueue(object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        assert(false)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (!call.isCanceled && response.isSuccessful && response.body() != null) {
                            // put it in ram cache and set the image view image

                            val bytes = response.body()!!.bytes() ?: return
                            // do not cache if there is not image
                            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size) ?: return

                            handler.post {
                                if (viewHolder.adapterPosition >= imageCaches!!.size) {
                                    assert(false)
                                }
                                imageCaches!![positionT] = Pair(searchResult.image_url, image)

                                // TODO this will also reset the animation, all we want is to update the image if the view is visible. Hint: LayoutManager
                                notifyItemChanged(positionT)
                            }
                        }
                    }
                })
            } else {
                viewHolder.imageImageView.setImageBitmap(imageCache.second)
            }

        } else {
            viewHolder.imageImageView.setImageBitmap(null)
        }
    }

    fun setSearchResults(searchResults: Array<Media>) {
        this.searchResults = searchResults
        imageCaches = Array(searchResults.size) {null}
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_search_result_text_title)
        val imageImageView: ImageView = itemView.findViewById(R.id.item_search_result_image_image)
    }
}
