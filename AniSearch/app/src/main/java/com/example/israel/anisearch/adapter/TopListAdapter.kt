package com.example.israel.anisearch.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.israel.anisearch.R
import com.example.israel.anisearch.model.data.Anime
import com.example.israel.anisearch.model.data.Character
import com.example.israel.anisearch.model.data.Manga
import com.example.israel.anisearch.model.data.Staff
import com.example.israel.anisearch.statics.AniListType
import kotlinx.android.synthetic.main.item_top_anime.view.*

class TopListAdapter(private val onItemClickedListener: OnItemClickedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var aniListType = AniListType.ANIME
    private var topList: MutableList<Any> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when (aniListType) {
            AniListType.ANIME -> AnimeViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_top_anime, p0, false))
            AniListType.MANGA -> MangaViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_top_anime, p0, false))
            AniListType.CHARACTER -> CharacterViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_top_anime, p0, false))
            AniListType.STAFF -> StaffViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_top_anime, p0, false))
        }
    }

    override fun getItemCount(): Int {
        return topList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val top = topList[position]

        when (viewHolder) {
            is AnimeViewHolder -> viewHolder.bind(top as Anime, onItemClickedListener)
            is MangaViewHolder -> viewHolder.bind(top as Manga, onItemClickedListener)
            is CharacterViewHolder -> viewHolder.bind(top as Character, onItemClickedListener)
            is StaffViewHolder -> viewHolder.bind(top as Staff, onItemClickedListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return aniListType.ordinal
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    fun setTopList(aniListType: AniListType, topList: MutableList<Any>) {
        this.aniListType = aniListType
        this.topList = topList
        notifyDataSetChanged()
    }

    class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(anime: Anime, onItemClickedListener: OnItemClickedListener) {

            val rankStr = "# ${adapterPosition + 1}"
            itemView.i_top_t_rank.text = rankStr

            itemView.i_top_t_name.text = anime.title?.english
            if (anime.coverImage?.medium != null) {
                itemView.i_top_pb_requesting_image.visibility = View.VISIBLE

                Glide
                    .with(itemView.context)
                    .load(anime.coverImage!!.medium)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .addListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.GONE
                            // TODO show failed image
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.GONE
                            return false
                        }
                    })
                    .into(itemView.i_top_i_image)

            } else { // no image url
                itemView.i_top_pb_requesting_image.visibility = View.GONE
                itemView.i_top_i_image.setImageBitmap(null)
            }

            itemView.i_top_i_image.transitionName = "top_image_transition" + anime.id.toString()

            itemView.setOnClickListener {
                val drawable = itemView.i_top_i_image.drawable
                val image: Bitmap?
                if (drawable is BitmapDrawable) {
                    image = drawable.bitmap
                } else {
                    image = null
                }

                onItemClickedListener.onItemClicked(it, itemView.i_top_i_image, AniListType.ANIME, anime.id ?: -1, image)
            }
        }
    }

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(manga: Manga, onItemClickedListener: OnItemClickedListener) {

            val rankStr = "# ${adapterPosition + 1}"
            itemView.i_top_t_rank.text = rankStr

            itemView.i_top_t_name.text = manga.title?.english
            if (manga.coverImage?.medium != null) {
                itemView.i_top_pb_requesting_image.visibility = View.VISIBLE

                Glide
                    .with(itemView.context)
                    .load(manga.coverImage!!.medium)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .addListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.GONE
                            // TODO show failed image
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.GONE
                            return false
                        }
                    })
                    .into(itemView.i_top_i_image)

            } else { // no image url
                itemView.i_top_pb_requesting_image.visibility = View.GONE
                itemView.i_top_i_image.setImageBitmap(null)
            }

            itemView.i_top_i_image.transitionName = "top_image_transition" + manga.id.toString()

            itemView.setOnClickListener {
                val drawable = itemView.i_top_i_image.drawable
                val image: Bitmap?
                if (drawable is BitmapDrawable) {
                    image = drawable.bitmap
                } else {
                    image = null
                }

                onItemClickedListener.onItemClicked(it, itemView.i_top_i_image, AniListType.MANGA, manga.id ?: -1, image)
            }
        }
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character, onItemClickedListener: OnItemClickedListener) {

            val rankStr = "# ${adapterPosition + 1}"
            itemView.i_top_t_rank.text = rankStr

            itemView.i_top_t_name.text = character.name?.getFullName()
            if (character.image?.medium != null) {
                itemView.i_top_pb_requesting_image.visibility = View.VISIBLE

                Glide
                    .with(itemView.context)
                    .load(character.image?.medium!!)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .addListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.GONE
                            // TODO show failed image
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.GONE
                            return false
                        }
                    })
                    .into(itemView.i_top_i_image)

            } else { // no image url
                itemView.i_top_pb_requesting_image.visibility = View.GONE
                itemView.i_top_i_image.setImageBitmap(null)
            }

            itemView.i_top_i_image.transitionName = "top_image_transition" + character.id.toString()

            itemView.setOnClickListener {
                val drawable = itemView.i_top_i_image.drawable
                val image: Bitmap?
                if (drawable is BitmapDrawable) {
                    image = drawable.bitmap
                } else {
                    image = null
                }

                onItemClickedListener.onItemClicked(it, itemView.i_top_i_image, AniListType.CHARACTER, character.id ?: -1, image)
            }
        }
    }

    class StaffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(staff: Staff, onItemClickedListener: OnItemClickedListener) {

            val rankStr = "# ${adapterPosition + 1}"
            itemView.i_top_t_rank.text = rankStr

            itemView.i_top_t_name.text = staff.name?.getFullName()
            if (staff.image?.medium != null) {
                itemView.i_top_pb_requesting_image.visibility = View.VISIBLE

                Glide
                    .with(itemView.context)
                    .load(staff.image?.medium!!)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .addListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.GONE
                            // TODO show failed image
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_top_pb_requesting_image.visibility = View.GONE
                            return false
                        }
                    })
                    .into(itemView.i_top_i_image)

            } else { // no image url
                itemView.i_top_pb_requesting_image.visibility = View.GONE
                itemView.i_top_i_image.setImageBitmap(null)
            }

            itemView.i_top_i_image.transitionName = "top_image_transition" + staff.id.toString()

            itemView.setOnClickListener {
                val drawable = itemView.i_top_i_image.drawable
                val image: Bitmap?
                if (drawable is BitmapDrawable) {
                    image = drawable.bitmap
                } else {
                    image = null
                }

                onItemClickedListener.onItemClicked(it, itemView.i_top_i_image, AniListType.STAFF, staff.id ?: -1, image)
            }
        }
    }

    interface OnItemClickedListener {
        fun onItemClicked(v: View, imageView: ImageView, aniListType: AniListType, topId: Int, image: Bitmap?)
    }

}