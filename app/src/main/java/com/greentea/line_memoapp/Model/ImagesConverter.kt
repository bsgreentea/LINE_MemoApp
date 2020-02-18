package com.greentea.line_memoapp.Model

import android.media.Image
import android.provider.MediaStore
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImagesConverter {

    @TypeConverter
    fun fromImgsJson(list: List<MediaStore.Images>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toImgsList(imgs: String): List<MediaStore.Images>{
        val notesType = object: TypeToken<List<MediaStore.Images>>() {}.type
        return Gson().fromJson<List<MediaStore.Images>>(imgs, notesType)
    }
}