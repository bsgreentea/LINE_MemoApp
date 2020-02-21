package com.greentea.line_memoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.Utils.Codes
import org.w3c.dom.Text

class MemoWriteActivity : AppCompatActivity() {

    lateinit var editTitle: TextView
    lateinit var editContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_write)

        editTitle = findViewById(R.id.edit_title)
        editContent = findViewById(R.id.edit_contents)

        if (intent.hasExtra("memo")) {
            val memo = intent.getSerializableExtra("memo") as Memo
            editTitle.setText(memo.memoTitle)
            editContent.setText(memo.memoContent)
            Toast.makeText(this, "메모를 수정합니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "새 메모를 작성합니다.", Toast.LENGTH_SHORT).show()
        }

        val saveBtn = findViewById<Button>(R.id.save_btn)
        saveBtn.setOnClickListener {

            // edit memo
            if(intent.hasExtra("memo")){

                Log.d("edited???","edited???")

                var memo = intent.getSerializableExtra("memo") as Memo
                memo.memoTitle = editTitle.text.toString()
                memo.memoContent = editContent.text.toString()

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

                    replyIntent.putExtra("title", title)
                    replyIntent.putExtra("content", content)

                    setResult(Codes.NEW_MEMO_REQUEST_CODE, replyIntent)

                    finish()
                }
            }
        }
    }
}