package com.example.tapandgo.screens.picker_location.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.tapandgo.R

class PositionsAdapter: BaseAdapter() {

    private val locations: MutableList<String> = mutableListOf()
    private var selectedPosition = 0

    fun setupLocations(newLocations: List<String>) {
        val newList: List<String> = listOf("Choose location") + newLocations
        locations.clear()
        locations.addAll(newList)
        notifyDataSetChanged()
    }

    fun selectLocations(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    fun getSelectedPosition() = selectedPosition

    fun getItems() = locations

    override fun getCount(): Int = locations.size

    override fun getItem(position: Int): String = locations[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_place_dropdown, parent, false)
            vh = ListRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        val item = getItem(position)
        vh.name.text = item

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_place_dropdown, parent, false)
            vh = ListRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        val item = getItem(position)
        vh.name.text = item

        return view
    }

    private class ListRowHolder(row: View?) {
        val name = row?.findViewById(R.id.name) as TextView
    }
}