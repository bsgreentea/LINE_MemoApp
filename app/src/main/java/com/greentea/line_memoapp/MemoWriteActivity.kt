package com.greentea.line_memoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class MemoWriteActivity : AppCompatActivity() {

    lateinit var editTitle: TextView
    lateinit var editContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_write)

        editTitle = findViewById(R.id.edit_title)
        editContent = findViewById(R.id.edit_contents)

        val saveBtn = findViewById<Button>(R.id.save_btn)
        saveBtn.setOnClickListener {

            val replyIntent = Intent()
            if(TextUtils.isEmpty(editTitle.text)){
                Toast.makeText(this, "제목을 입력하세요.",Toast.LENGTH_SHORT).show()
            } else {

                val title = editTitle.text.toString()
                val content = editContent.text.toString()

                replyIntent.putExtra("title", title)
                replyIntent.putExtra("content", content)

                setResult(Activity.RESULT_OK, replyIntent)

                finish()
            }
        }
    }
}