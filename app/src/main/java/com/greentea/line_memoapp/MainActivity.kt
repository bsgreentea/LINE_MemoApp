package com.greentea.line_memoapp

import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.greentea.line_memoapp.Adapter.MemoViewAdapter
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.ViewModel.MemoViewModel

class MainActivity : AppCompatActivity() {

    val NEW_MEMO_REQUEST_CODE = 1
    lateinit var addButton: FloatingActionButton
    lateinit var memoViewModel: MemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init(){

        val adapter = MemoViewAdapter(this)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter

        val lm = LinearLayoutManager(this)
        recyclerView.layoutManager = lm

        memoViewModel = ViewModelProvider(this).get(MemoViewModel::class.java)
        memoViewModel.allMemos.observe(this, Observer { memos->
            memos?.let{adapter.setMemos(it)}
        })

        addButton = findViewById(R.id.fab)
        addButton.setOnClickListener{
            val intent = Intent(this@MainActivity, MemoWriteActivity::class.java)
            startActivityForResult(intent, NEW_MEMO_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_MEMO_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val title = data!!.getStringExtra("title").toString()
            val content = data!!.getStringExtra("content").toString()
            val memo = Memo(0,title,content)

            memoViewModel.insert(memo)
        }
    }
}
