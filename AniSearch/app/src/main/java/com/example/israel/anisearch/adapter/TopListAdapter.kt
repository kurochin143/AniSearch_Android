package com.example.israel.anisearch.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.israel.anisearch.R
import com.example.israel.anisearch.jikan_api.JikanResult
import com.example.israel.anisearch.network.NetworkStatics
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class TopListAdapter : RecyclerView.Adapter<TopListAdapter.ViewHolder>() {
    private var topList: Array<JikanResult> = arrayOf()
    private var imageCaches: Array<Pair<String?, Bitmap?>?> = arrayOf()
    private var isRequestingImages: Array<Boolean> = arrayOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_top, p0, false))
        return viewHolder
    }

    override fun getItemCount(): Int {
        return topList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val top = topList[position]

        viewHolder.titleTextView.text = top.title?: "no_title"

        if (top.imageUrl != null) {
            val imageCache = imageCaches[viewHolder.adapterPosition]
            if (imageCache == null) { // image has not been cached yet

                viewHolder.imageImageView.setImageBitmap(null)
                viewHolder.requestingImageProgressBar.visibility = View.VISIBLE

                if (!isRequestingImages[viewHolder.adapterPosition]) { // not already downloading
                    // download the image
                    isRequestingImages[viewHolder.adapterPosition] = true

                    val uiHandler = Handler(viewHolder.itemView.context.mainLooper)

                    // preserve value
                    val positionT = viewHolder.adapterPosition
                    val context = viewHolder.itemView.context

                    NetworkStatics.requestImage(top.imageUrl!!).enqueue(object: Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            e.printStackTrace()
                            onRequestImageFinished(uiHandler, positionT, null, call,null)
                        }

                        override fun onResponse(call: Call, response: Response) {
                            onRequestImageFinished(uiHandler, positionT, top.imageUrl, call, response)
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
            val bytes = response.body()!!.bytes() ?: return
            // do not cache if there is no image
            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size) ?: return

            uiHandler.post {
                isRequestingImages[position] = false

                imageCaches[position] = Pair(url, image)

                // TODO this will also reset the animation, all we want is to update the image if the view is visible. Hint: LayoutManager
                notifyItemChanged(position)
            }
        } else {
            uiHandler.post {
                isRequestingImages[position] = false
                notifyItemChanged(position)
            }
        }
    }

    fun setTopList(topList: Array<JikanResult>) {
        this.topList = topList
        imageCaches = Array(topList.size) {null}
        isRequestingImages = Array(topList.size) { false}
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_top_text_title)
        val imageImageView: ImageView = itemView.findViewById(R.id.item_top_image_image)
        val requestingImageProgressBar: ProgressBar = itemView.findViewById(R.id.item_top_progress_bar_requesting_image)
    }
}