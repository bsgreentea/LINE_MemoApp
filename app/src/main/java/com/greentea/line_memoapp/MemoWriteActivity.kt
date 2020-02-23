package com.greentea.line_memoapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.greentea.line_memoapp.Adapter.ImageAdapter
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.Utils.Codes
import gun0912.tedbottompicker.TedBottomPicker
import java.lang.StringBuilder


class MemoWriteActivity : AppCompatActivity() {

    lateinit var editTitle: TextView
    lateinit var editContent: TextView

    private var selectedUriList: MutableList<Uri> = mutableListOf()
    lateinit var adapter: ImageAdapter
    lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_memo_write)
        setContentView(R.layout.activity_memo_write)

        init()

        if (intent.hasExtra("memo")) {
            val memo = intent.getSerializableExtra("memo") as Memo
            editTitle.setText(memo.memoTitle)
            editContent.setText(memo.memoContent)
            Toast.makeText(this, "메모를 수정합니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "새 메모를 작성합니다.", Toast.LENGTH_SHORT).show()
        }

        val saveBtn = findViewById<FloatingActionButton>(R.id.fab_save)
        saveBtn.setOnClickListener {

            // edit memo
            if (intent.hasExtra("memo")) {

                var memo = intent.getSerializableExtra("memo") as Memo
                memo.memoTitle = editTitle.text.toString()
                memo.memoContent = editContent.text.toString()
                memo.images = makeString(adapter.images)

                val replyIntent = Intent()
                replyIntent.putExtra("memo", memo)
                setResult(Codes.EDIT_MEMO_REQUEST_CODE, replyIntent)

                finish()

            } else { // new memo

                val replyIntent = Intent()
                if (TextUtils.isEmpty(editTitle.text)) {
                    Toast.makeText(this, "제목을 입력하세요.", Toast.LENGTH_SHORT).show()
                } else {

                    val title = editTitle.text.toString()
                    val content = editContent.text.toString()

                    val memo = Memo(0, title, content, makeString(adapter.images))

                    replyIntent.putExtra("memo",memo)

                    setResult(Codes.NEW_MEMO_REQUEST_CODE, replyIntent)

                    finish()
                }
            }
        }
    }

    fun init(){

        recyclerview = findViewById(R.id.img_recyclerview)
        adapter = ImageAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        adapter.setImages(selectedUriList)

        editTitle = findViewById(R.id.edit_title)
        editContent = findViewById(R.id.edit_contents)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_image_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.gallery_camera -> {
                select()
                true
            }
            R.id.url -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun select(){

        // select multiple images
//        TedBottomPicker.with(this) //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
//            .setPeekHeight(1600)
//            .showTitle(false)
//            .setCompleteButtonText("Done")
//            .setEmptySelectionText("No Select")
//            .setSelectedUriList(selectedUriList)
//            .showMultiImage { uriList: List<Uri> ->
////                selectedUriList.addAll(uriList)
//                adapter.setImages(selectedUriList)
//            }

        TedBottomPicker.with(this)
            .show {
                adapter.addImage(it)
            }
    }

    fun makeString(list: List<Uri>): String{

        var sb = StringBuilder()

        val sz = list.size
        for (i in 0..sz-1) {
            sb.append(list[i].toString())
            if (i != sz - 1) sb.append('\n')
        }

        return sb.toString()
    }
}