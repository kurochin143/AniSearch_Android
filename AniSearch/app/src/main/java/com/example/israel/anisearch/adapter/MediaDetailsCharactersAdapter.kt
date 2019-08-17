package com.example.israel.anisearch.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.israel.anisearch.R
import com.example.israel.anisearch.model.data.Character
import com.example.israel.anisearch.model.data.CharacterConnection
import com.example.israel.anisearch.model.data.Page
import kotlinx.android.synthetic.main.item_character.view.*

class MediaDetailsCharactersAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<MediaDetailsCharactersAdapter.ContentViewHolder>() {

    private var characterConnection = CharacterConnection(
        mutableListOf(),
        Page.PageInfo(0, 0)
    )

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContentViewHolder {
        return ContentViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_character, p0, false))
    }

    override fun getItemCount(): Int {
        return characterConnection.edges!!.size
    }

    override fun onBindViewHolder(p0: ContentViewHolder, p1: Int) {
        val character = characterConnection.edges!![p1].node!!

        p0.bind(character)
    }

    fun setCharacterConnection(characterConnection: CharacterConnection) {
        this.characterConnection = characterConnection
        notifyDataSetChanged()
    }

    fun addCharacterConnection(characterConnection: CharacterConnection) {
        val oldSize = this.characterConnection.edges!!.size
        this.characterConnection.pageInfo = characterConnection.pageInfo
        this.characterConnection.edges!!.addAll(characterConnection.edges!!)
        notifyItemRangeChanged(oldSize, characterConnection.edges!!.size + 1)
    }

    class ContentViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(character: Character) {
            itemView.i_character_t_name.text = character.name?.getFullName()

            if (character.image?.medium != null) {
                itemView.i_character_pb_requesting_image.visibility = View.VISIBLE

                Glide
                    .with(itemView.context)
                    .load(character.image!!.medium!!)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .addListener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_character_pb_requesting_image.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.i_character_pb_requesting_image.visibility = View.GONE
                            return false
                        }
                    })
                    .into(itemView.i_character_i_image)

            } else {
                itemView.i_character_pb_requesting_image.visibility = View.GONE
                itemView.i_character_i_image.setImageBitmap(null)
            }
        }
    }

}