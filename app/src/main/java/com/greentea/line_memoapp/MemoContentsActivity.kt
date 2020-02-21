package com.greentea.line_memoapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val intent = intent
        val memo = intent.getSerializableExtra("memo") as Memo

        val title = memo.memoTitle
        val content = memo.memoContent

        textTitle.setText(title)
        textContents.setText(content)

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
}