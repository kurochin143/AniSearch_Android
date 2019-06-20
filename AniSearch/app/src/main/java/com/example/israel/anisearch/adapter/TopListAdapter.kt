package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
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
import com.example.israel.anisearch.model.Top
import kotlinx.android.synthetic.main.item_top.view.*

class TopListAdapter(private val onItemClickedListener: OnItemClickedListener) : RecyclerView.Adapter<TopListAdapter.ViewHolder>() {
    private var topList: MutableList<Top> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_top, p0, false))
    }

    override fun getItemCount(): Int {
        return topList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val top = topList[position]
        viewHolder.bind(top, onItemClickedListener)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    fun setTopList(topList: MutableList<Top>) {
        this.topList = topList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(top: Top, onItemClickedListener: OnItemClickedListener) {

            val rankStr = "# ${adapterPosition + 1}"
            itemView.i_top_t_rank.text = rankStr

            itemView.i_top_t_name.text = top.name
            if (top.imageUrl != null) {
                itemView.i_top_pb_requesting_image.visibility = View.VISIBLE

                Glide
                    .with(itemView.context)
                    .load(top.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .addListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.INVISIBLE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.INVISIBLE
                            return false
                        }

                    })
                    .into(itemView.i_top_i_image)

            } else { // no image url
                itemView.i_top_pb_requesting_image.visibility = View.INVISIBLE
                itemView.i_top_i_image.setImageBitmap(null)
            }

            itemView.i_top_i_image.transitionName = "top_image_transition" + top.id.toString()

            itemView.setOnClickListener {
                val drawable = itemView.i_top_i_image.drawable
                val image: Bitmap?
                if (drawable is BitmapDrawable) {
                    image = drawable.bitmap
                } else {
                    image = null
                }

                onItemClickedListener.onItemClicked(it, itemView.i_top_i_image, top, image)
            }
        }
    }

    interface OnItemClickedListener {
        fun onItemClicked(v: View, imageView: ImageView, top: Top, image: Bitmap?)
    }
}