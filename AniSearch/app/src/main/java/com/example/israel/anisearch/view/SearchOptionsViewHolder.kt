package com.example.israel.anisearch.view

import android.content.Context
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.israel.anisearch.R

class SearchOptionsViewHolder(private val root: ViewGroup, private val defaultName: String, private val defaultColor: Int){

    private val _nameList = ArrayList<String>()

    var names = _nameList
        get() = _nameList
        private set

    init {
        addOptionInternal(defaultName, defaultColor)
    }

    fun addOption(name: String, color: Int) {
        if (_nameList[0] == defaultName) {
            removeOptionInternal(0)
        }

        addOptionInternal(name, color)
    }

    fun removeOption(i: Int) {
        if (_nameList[i] == defaultName) {
            return
        }

        removeOptionInternal(i)

        if (_nameList.size == 0) {
            // add the default
            addOptionInternal(defaultName, defaultColor)
        }
    }

    private fun addOptionInternal(name: String, color: Int) {
        _nameList.add(name)
        val viewIndex = root.childCount
        val inflater = root.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_search_option, root)
        val view = root.getChildAt(viewIndex) as CardView
        view.setCardBackgroundColor(color)
        view.setOnClickListener {
            removeOption(viewIndex)
        }

        val nameTextView = view.findViewById<TextView>(R.id.search_option_text_name)
        nameTextView.text = name
    }

    private fun removeOptionInternal(i: Int) {
        _nameList.removeAt(i)
        root.removeViewAt(i)
    }

}