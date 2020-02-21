package com.greentea.line_memoapp.Model

import android.media.Image
import android.provider.MediaStore
import androidx.room.*
import java.io.Serializable

@Entity(tableName = "memo_table")
data class Memo(
    @PrimaryKey(autoGenerate = true) var memoId: Int,
    @ColumnInfo(name = "memoTitle") var memoTitle: String,
    @ColumnInfo(name = "memoContent") var memoContent: String//,
//    @ColumnInfo(name = "images")
//    @TypeConverters(ImagesConverter::class)
//    var Imgs: List<MediaStore.Images>
) : Serializable