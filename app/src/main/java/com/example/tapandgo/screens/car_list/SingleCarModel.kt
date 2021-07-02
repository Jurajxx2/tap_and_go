package com.example.tapandgo.screens.car_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewParent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.airbnb.epoxy.*
import com.bumptech.glide.Glide
import com.example.tapandgo.R
import com.example.tapandgo.databinding.ListItemCarListBinding
import com.example.tapandgo.helpers.KotlinEpoxyHolder
import kotlin.coroutines.coroutineContext

@EpoxyModelClass
abstract class SingleCarModel :EpoxyModelWithHolder<SingleCarModel.CarHolder>(){

    override fun getDefaultLayout() = R.layout.list_item_car_list

    @EpoxyAttribute
    var image: String = ""

    @EpoxyAttribute
    var name: String = ""

    @EpoxyAttribute
    var year: String = ""

    @EpoxyAttribute
    var data: String = ""

    @EpoxyAttribute
    var action: () -> Unit = {}


    override fun bind(holder: CarHolder) {
        Glide.with(holder.image)
            .load(image)
            .placeholder(R.drawable.splash_hero)
            .into(holder.image)
        holder.name.text = name
        holder.year.text = year
        holder.data.text = data

        holder.detail.setOnClickListener {
            action()
        }
    }

    inner class CarHolder: KotlinEpoxyHolder(){
        val image by bind<ImageView>(R.id.car_image)
        val name by bind<TextView>(R.id.car_name)
        val year by bind<TextView>(R.id.car_year)
        val data by bind<TextView>(R.id.car_data)
        val detail by bind<Button>(R.id.car_detail_button)
    }
}