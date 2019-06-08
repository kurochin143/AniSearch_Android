package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import com.example.israel.anisearch.model.Top

class TopListAdapter(private val onItemClickedListener: OnItemClickedListener) : RecyclerView.Adapter<TopListAdapter.ViewHolder>() {
    private var topList: MutableList<Top> = ArrayList()

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
        if (top.imageUrl != null) {
            viewHolder.requestingImageProgressBar.visibility = View.VISIBLE

            Glide
                .with(viewHolder.itemView.context)
                .load(top.imageUrl)
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

        viewHolder.itemView.setOnClickListener {
            val drawable = viewHolder.imageImageView.drawable
            var image: Bitmap?
            if (drawable is BitmapDrawable) {
                image = drawable.bitmap
            } else {
                image = null
            }

            onItemClickedListener.onItemClicked(top, image)
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    fun setTopList(topList: MutableList<Top>) {
        this.topList = topList
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