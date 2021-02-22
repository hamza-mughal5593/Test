package com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject.DetailActivity
import com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject.Model.QuestionsModel
import com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject.R

class ReviewAdapter (val activity: Activity?, val reviewModel: ArrayList<QuestionsModel>?, val context: Context?) :
RecyclerView.Adapter<ReviewAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val layoutInflater = LayoutInflater.from(context)
        view = layoutInflater.inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




        holder.name.text = reviewModel?.get(position)?.name
        holder.price.text = reviewModel?.get(position)?.price
        holder.created_at.text = reviewModel?.get(position)?.created_at
//        holder.initClickListener()
        if (activity != null) {
            Glide
                .with(activity)
                .load(reviewModel?.get(position)?.image_urls_thumbnails)
                .centerCrop()

                .into(holder.thumb)
        }

        holder.layout_main.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
// To pass any data to next activity
            intent.putExtra("image_urls", reviewModel?.get(position)?.image_urls)
// start your next activity
            activity?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return reviewModel!!.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var thumb: ImageView
        var created_at: TextView
        var price: TextView
        var name: TextView
        var layout_main: LinearLayout



//        var place_name: TextView
//        var cat_icon: ImageView


        init {
            thumb = itemView.findViewById(R.id.thumb)
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.price)
            created_at = itemView.findViewById(R.id.created_at)
            layout_main = itemView.findViewById(R.id.layout_main)

        }
    }

}