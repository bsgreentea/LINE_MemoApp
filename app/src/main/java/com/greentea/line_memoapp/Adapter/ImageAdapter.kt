package com.greentea.line_memoapp.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.greentea.line_memoapp.R
import java.net.URI

class ImageAdapter(val context: Context) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var images = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(images[position])

        holder.itemView.setOnClickListener {
            images.removeAt(position)
//            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView = view.findViewById<ImageView>(R.id.memo_image)

        fun bind(uri: Uri) {
            imageView.setImageURI(uri)
//            showUri(uri, imageView)
        }
    }

    internal fun setImages(images: List<Uri>) {
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    internal fun addImage(uri: Uri){
        this.images.add(uri)
        notifyDataSetChanged()
    }

    private fun showUri(uri: Uri, imageView: ImageView) {

        var requestManager = Glide.with(context)

        val widthPixel = 80
        val heightPixel = 80
//        val imageHolder: View = LayoutInflater.from(context).inflate(R.layout.image_item, null)
        requestManager
            .load(uri.toString())
            .apply(RequestOptions().fitCenter())
            .into(imageView)
//        imageView.setLayoutParams(FrameLayout.LayoutParams(widthPixel, heightPixel))
    }
}