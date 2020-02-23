package com.greentea.line_memoapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
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

    lateinit var adapter: ImageAdapter
    lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_write)

        init()

        val saveBtn = findViewById<FloatingActionButton>(R.id.fab_save)
        saveBtn.setOnClickListener {

            if (TextUtils.isEmpty(editTitle.text)) {
                Toast.makeText(this, "제목을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {

                val replyIntent = Intent()
                val title = editTitle.text.toString()
                val content = editContent.text.toString()
                val images = adapter.images

                if (intent.hasExtra("memo")) { // eidt memo

                    var memo = intent.getSerializableExtra("memo") as Memo

                    val id = memo.memoId

                    memo = Memo(id, title, content, makeString(images))
                    replyIntent.putExtra("memo", memo)

                    setResult(Codes.EDIT_MEMO_REQUEST_CODE, replyIntent)

                } else { // new memo

                    val memo = Memo(0, title, content, makeString(images))

                    replyIntent.putExtra("memo", memo)
                    setResult(Codes.NEW_MEMO_REQUEST_CODE, replyIntent)
                }

                finish()
            }
        }
    }

    fun init() {

        recyclerview = findViewById(R.id.img_recyclerview)
        adapter = ImageAdapter(this, Codes.EDIT_MODE)
        recyclerview.adapter = adapter
        recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        editTitle = findViewById(R.id.edit_title)
        editContent = findViewById(R.id.edit_contents)

        if (intent.hasExtra("memo")) {

            val memo = intent.getSerializableExtra("memo") as Memo
            editTitle.setText(memo.memoTitle)
            editContent.setText(memo.memoContent)
            adapter.setImages(makeUriList(memo.images))
            Toast.makeText(this, "메모를 수정합니다.", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "새 메모를 작성합니다.", Toast.LENGTH_SHORT).show()
        }
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

                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.url_dialog, null)
                val dialogText = dialogView.findViewById<EditText>(R.id.edit_url)

                builder.setView(dialogView)
                    .setPositiveButton("추가"){ dialogInterface, i ->
                        dialogText.text.toString()
                    }
                    .setNegativeButton("취소"){dialogInterface, i ->

                    }
                    .show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun select() {

        TedBottomPicker.with(this)
            .show {
                adapter.addImage(it)
            }
    }

    fun makeString(list: List<Uri>): String {

        if(list.isEmpty()) return ""

        var sb = StringBuilder()

        val sz = list.size
        for (i in 0..sz - 1) {
            sb.append(list[i].toString())
            if (i != sz - 1) sb.append('\n')
        }

        return sb.toString()
    }

    fun makeUriList(str: String): List<Uri> {

        if(str.equals("")) return emptyList()

        return str.split('\n').map { Uri.parse(it) }.toList()
    }
}