package com.greentea.line_memoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.greentea.line_memoapp.Adapter.MemoViewAdapter
import com.greentea.line_memoapp.Model.Memo
import com.greentea.line_memoapp.Utils.Codes
import com.greentea.line_memoapp.ViewModel.MemoViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

class MainActivity : AppCompatActivity() {

    lateinit var addButton: FloatingActionButton
    lateinit var memoViewModel: MemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {

        checkPermission()

        val adapter = MemoViewAdapter(this)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter

        val lm = LinearLayoutManager(this)
        recyclerView.layoutManager = lm

        memoViewModel = ViewModelProvider(this).get(MemoViewModel::class.java)
        memoViewModel.allMemos.observe(this, Observer { memos ->
            memos?.let { adapter.setMemos(it) }
        })

        addButton = findViewById(R.id.fab)
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, MemoWriteActivity::class.java)
            startActivityForResult(intent, Codes.NEW_MEMO_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Codes.NEW_MEMO_REQUEST_CODE) {

            val title = data!!.getStringExtra("title").toString()
            val content = data!!.getStringExtra("content").toString()
            val memo = Memo(0, title, content)

            memoViewModel.insert(memo)

        } else if (resultCode == Codes.EDIT_MEMO_REQUEST_CODE) {
            memoViewModel.insert(data!!.getSerializableExtra("memo") as Memo)
        } else if (resultCode == Codes.DELETE_RESULT_CODE) { // delete memo
            memoViewModel.delete(data!!.getSerializableExtra("memo") as Memo)
        }
    }

    fun checkPermission(){

        var permissionListener: PermissionListener = object : PermissionListener{
            override fun onPermissionGranted() {
                return
            }
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                finish()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage("앱의 사용을 위해 권한이 필요합니다.")
            .setDeniedMessage("[설정] -> [권한]에서 권한을 설정할 수 있습니다.")
            .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()
    }
}
