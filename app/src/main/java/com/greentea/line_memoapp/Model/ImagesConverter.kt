package com.greentea.line_memoapp.Model

import android.media.Image
import android.provider.MediaStore
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImagesConverter {

    @TypeConverter
    fun fromImgsJson(list: List<Image>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toImgsList(imgs: String): List<Image>{
        val notesType = object: TypeToken<List<Image>>() {}.type
        return Gson().fromJson<List<Image>>(imgs, notesType)
    }
}