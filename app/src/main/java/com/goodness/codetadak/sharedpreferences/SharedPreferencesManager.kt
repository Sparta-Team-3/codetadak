package com.goodness.codetadak.sharedpreferences

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.goodness.codetadak.Constants
import com.goodness.codetadak.api.responses.VideosResponse
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import android.util.Base64

class SharedPreferencesManager(context: Context) {
    private val prefs = context.getSharedPreferences(Constants.PREFS_FILENAME,Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveMyFavorite (list: MutableList<VideosResponse>) {
        val json = gson.toJson(list)
        prefs.edit().putString(Constants.KEY_FAVORITE,json).apply()
    }

    fun loadMyFavorite () : MutableList<VideosResponse>{
        var result : MutableList<VideosResponse> = mutableListOf()
        if(prefs.contains(Constants.KEY_FAVORITE)) {
            val json = prefs.getString(Constants.KEY_FAVORITE, Constants.DEFAULT_STRING)
            try {
                val typeToken = object : TypeToken<MutableList<VideosResponse>>() {}.type
                result  = gson.fromJson(json, typeToken)
            } catch (e: JsonParseException) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun saveUserProfile (profileImage : Drawable, name: String, info: String) {
        val stream = ByteArrayOutputStream()
        profileImage.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream) // Drawable을 Bitmap으로 변환
        val byteArray = stream.toByteArray()
        prefs.edit().putString(Constants.KEY_PROFILE_IMAGE, Base64.encodeToString(byteArray, Base64.DEFAULT)).apply()
        prefs.edit().putString(Constants.KEY_PROFILE_NAME, name).apply()
        prefs.edit().putString(Constants.KEY_PROFILE_INFO,info).apply()

    }

    fun loadUserProfile() {
        val storedProfileImage = prefs.getString(Constants.KEY_PROFILE_IMAGE, Constants.DEFAULT_STRING)
        if (storedProfileImage != "") {
            val byteArray =
                Base64.decode(storedProfileImage, Base64.DEFAULT) // Base64 문자열을 Bitmap으로 디코딩
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            prefs.getString(Constants.KEY_PROFILE_NAME , Constants.DEFAULT_STRING)
            prefs.getString(Constants.KEY_PROFILE_INFO , Constants.DEFAULT_STRING)

        }
    }

}