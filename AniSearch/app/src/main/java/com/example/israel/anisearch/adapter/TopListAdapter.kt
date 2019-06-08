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
import com.example.israel.anisearch.model.Top
import com.example.israel.anisearch.network.NetworkStatics
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.BufferedInputStream
import java.io.IOException

class TopListAdapter(private val onItemClickedListener: OnItemClickedListener) : RecyclerView.Adapter<TopListAdapter.ViewHolder>() {
    private var topList: MutableList<Top> = ArrayList()
    private var imageCaches: MutableList<Pair<String?, Bitmap?>?> = ArrayList()
    private var isRequestingImages: MutableList<Boolean> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_top, p0, false))
        return viewHolder
    }

    override fun getItemCount(): Int {
        return topList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val top = topList[position]

        val rankStr = "# ${position + 1}"
        viewHolder.rankTextView.text = rankStr

        viewHolder.titleTextView.text = top.name

        val imageUrl: String? = top.imageUrl
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

                            // @NOTE: this will request a download again
                            notifyItemChanged(positionT)
                        }
                    }

                    NetworkStatics.requestImage(imageUrl).enqueue(object: Callback {
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

        viewHolder.itemView.setOnClickListener {
            onItemClickedListener.onItemClicked(top, imageCaches[viewHolder.adapterPosition]?.second)
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    fun setTopList(topList: MutableList<Top>) {
        this.topList = topList
        imageCaches = ArrayList(topList.size)
        isRequestingImages = ArrayList(topList.size)
        repeat(this.topList.size) {
            imageCaches.add(null)
            isRequestingImages.add(false)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rankTextView: TextView = itemView.findViewById(R.id.item_top_text_rank)
        val titleTextView: TextView = itemView.findViewById(R.id.item_top_text_title)
        val imageImageView: ImageView = itemView.findViewById(R.id.item_top_image_image)
        val requestingImageProgressBar: ProgressBar = itemView.findViewById(R.id.item_top_progress_bar_requesting_image)
    }

    interface OnItemClickedListener {
        fun onItemClicked(top: Top, image: Bitmap?)
    }
}