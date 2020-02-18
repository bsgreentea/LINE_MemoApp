package com.greentea.line_memoapp

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greentea.line_memoapp.Adapter.MemoViewAdapter
import com.greentea.line_memoapp.Model.Memo

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var temp = arrayListOf<Memo>()
        temp.add(Memo(0, "title1", "content1"))
        temp.add(Memo(0, "title2", "content2"))

        val adapter = MemoViewAdapter(this, temp)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter

        val lm = LinearLayoutManager(this)
        recyclerView.layoutManager = lm
    }
}
