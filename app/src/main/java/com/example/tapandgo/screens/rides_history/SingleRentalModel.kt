package com.example.tapandgo.screens.rides_history

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.tapandgo.R
import com.example.tapandgo.helpers.KotlinEpoxyHolder

@EpoxyModelClass
abstract class SingleRentalModel : EpoxyModelWithHolder<SingleRentalModel.RentalHolder>(){

    override fun getDefaultLayout() = R.layout.list_item_rental_list

    @EpoxyAttribute
    var image: String = ""

    @EpoxyAttribute
    var name: String = ""

    @EpoxyAttribute
    var status: String = ""

    @EpoxyAttribute
    var date: String = ""

    override fun bind(holder: RentalHolder) {
        Glide.with(holder.image)
            .load(image)
            .placeholder(R.drawable.splash_hero)
            .into(holder.image)
        holder.name.text = name
        holder.status.text = status
        holder.date.text = date
    }

    inner class RentalHolder: KotlinEpoxyHolder(){
        val image by bind<ImageView>(R.id.car_image)
        val name by bind<TextView>(R.id.car_name)
        val status by bind<TextView>(R.id.rental_status)
        val date by bind<TextView>(R.id.rental_time)
    }
}