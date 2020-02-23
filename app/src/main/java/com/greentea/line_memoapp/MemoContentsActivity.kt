package com.greentea.line_memoapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.greentea.line_memoapp.Adapter.ImageAdapter
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.Utils.Codes

class MemoContentsActivity : AppCompatActivity() {

    lateinit var textTitle: TextView
    lateinit var textContents: TextView
    lateinit var editBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_contents)

        init()

        val memo = intent.getSerializableExtra("memo") as Memo

        editBtn.setOnClickListener {

            var intent = Intent(this, MemoWriteActivity::class.java)
            intent.putExtra("memo", memo)
            intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)

            startActivity(intent)
            finish()
        }
    }

    fun init(){

        textTitle = findViewById(R.id.text_title)
        textContents = findViewById(R.id.text_contents)
        editBtn = findViewById(R.id.fab_edit)

        var recyclerview = findViewById<RecyclerView>(R.id.detail_img_recyclerview)
        var adapter = ImageAdapter(this, Codes.NO_EDIT_MODE)
        recyclerview.adapter = adapter
        recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val memo = intent.getSerializableExtra("memo") as Memo

        textTitle.setText(memo.memoTitle)
        textContents.setText(memo.memoContent)
        adapter.setImages(makeUriList(memo.images))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete ->{

                var replyIntent = Intent()
                replyIntent.putExtra("memo", intent.getSerializableExtra("memo") as Memo)
                setResult(Codes.DELETE_RESULT_CODE, replyIntent)

                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun makeUriList(str: String): List<Uri> {

        if(str.equals("")) return emptyList()

        return str.split('\n').map { Uri.parse(it) }.toList()
    }
}