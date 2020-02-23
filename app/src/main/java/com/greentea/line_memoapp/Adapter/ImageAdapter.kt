package com.greentea.line_memoapp.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greentea.line_memoapp.R
import com.greentea.line_memoapp.Utils.Codes

class ImageAdapter(val context: Context, val mode: Int) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var images = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(images[position])

        holder.itemView.setOnClickListener {
            if(mode == Codes.EDIT_MODE) {
                images.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView = view.findViewById<ImageView>(R.id.memo_image)

        fun bind(uri: Uri) {
            Glide.with(context).load(uri).error(R.drawable.img_error).into(imageView)
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
}