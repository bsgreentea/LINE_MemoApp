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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.Utils.Codes
import gun0912.tedbottompicker.TedBottomPicker


class MemoWriteActivity : AppCompatActivity() {

    lateinit var editTitle: TextView
    lateinit var editContent: TextView

    private var selectedUriList: List<Uri>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_memo_write)
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

        val saveBtn = findViewById<FloatingActionButton>(R.id.fab_save)
        saveBtn.setOnClickListener {

            // edit memo
            if (intent.hasExtra("memo")) {

                Log.d("edited???", "edited???")

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
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun select(){

        TedBottomPicker.with(this) //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
            .setPeekHeight(1600)
            .showTitle(false)
            .setCompleteButtonText("Done")
            .setEmptySelectionText("No Select")
            .setSelectedUriList(selectedUriList)
            .showMultiImage { uriList: List<Uri> ->
                selectedUriList = uriList
                showUriList(uriList)
            }

        if(selectedUriList != null)
            Log.d("show_uri_list", "" + selectedUriList!![0].toString())
    }

    private fun showUriList(uriList: List<Uri>) { // Remove all views before
// adding the new ones.

        var mSelectedImagesContainer = findViewById<ViewGroup>(R.id.selected_photos_container)
        var iv_image = findViewById<ImageView>(R.id.iv_image)
        var requestManager = Glide.with(this)

        mSelectedImagesContainer.removeAllViews()
        iv_image.setVisibility(View.GONE)
        mSelectedImagesContainer.setVisibility(View.VISIBLE)
        val widthPixel = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            100f,
            resources.displayMetrics
        ).toInt()
        val heightPixel = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            100f,
            resources.displayMetrics
        ).toInt()
        for (uri in uriList) {
            val imageHolder: View = LayoutInflater.from(this).inflate(R.layout.image_item, null)
            val thumbnail: ImageView = imageHolder.findViewById(R.id.media_image)
            requestManager
                .load(uri.toString())
                .apply(RequestOptions().fitCenter())
                .into(thumbnail)
            mSelectedImagesContainer.addView(imageHolder)
            thumbnail.setLayoutParams(FrameLayout.LayoutParams(widthPixel, heightPixel))
        }
    }
}