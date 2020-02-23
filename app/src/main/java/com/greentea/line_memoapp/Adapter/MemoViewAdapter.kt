package com.greentea.line_memoapp.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greentea.line_memoapp.MemoContentsActivity
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.R
import com.greentea.line_memoapp.Utils.Codes

class MemoViewAdapter(val context: Context) : RecyclerView.Adapter<MemoViewAdapter.ViewHolder>() {

    var memos = emptyList<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.memo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = memos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bind(memos[position])

        holder?.itemView.setOnClickListener {

            var context = it.context
            var intent = Intent(context, MemoContentsActivity::class.java)

            intent.putExtra("memo", memos[position])
            (context as Activity).startActivityForResult(intent, Codes.EDIT_MEMO_REQUEST_CODE)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title = view.findViewById<TextView>(R.id.tv1)
        val contents = view.findViewById<TextView>(R.id.tv2)
        val imageView = view.findViewById<ImageView>(R.id.iv_thumbnail)

        fun bind(memo: Memo) {
            title.setText(memo.memoTitle)
            contents.setText(memo.memoContent)

            if (!memo.images.equals("")) {
                val list = memo.images.split('\n').map { it }.toList()
                Glide.with(context).load(Uri.parse(list[0])).error(R.drawable.img_error).into(imageView)
            }
            else imageView.setImageResource(R.drawable.ic_image_black_24dp)
        }
    }

    internal fun setMemos(memos: List<Memo>) {
        this.memos = memos
        notifyDataSetChanged()
    }
}