package com.greentea.line_memoapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.R

class MemoViewAdapter(val context: Context) : RecyclerView.Adapter<MemoViewAdapter.ViewHolder>() {

    var memos = emptyList<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.memo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = memos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bind(memos[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title = view.findViewById<TextView>(R.id.tv1)
        val contents = view.findViewById<TextView>(R.id.tv2)

        fun bind(memo: Memo) {
            title.setText(memo.memoTitle)
            contents.setText(memo.memoContent)
        }
    }

    internal fun setMemos(memos: List<Memo>){
        this.memos = memos
        notifyDataSetChanged()
    }
}