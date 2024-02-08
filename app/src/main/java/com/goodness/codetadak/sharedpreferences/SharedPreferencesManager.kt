package com.goodness.codetadak.sharedpreferences

import android.content.Context
import android.net.Uri
import com.goodness.codetadak.Constants
import com.goodness.codetadak.api.responses.VideosResponse
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import androidx.core.net.toUri
import com.goodness.codetadak.api.responses.VideoItem

class SharedPreferencesManager(context: Context) {
    private val prefs = context.getSharedPreferences(Constants.PREFS_FILENAME,Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveMyFavorite (list: List<VideoItem>) {
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

    fun saveUserProfile (userInfo: UserInfo) { // null 체크
        prefs.edit().putString(Constants.KEY_PROFILE_IMAGE, userInfo.profileImage.toString()).apply()
        prefs.edit().putString(Constants.KEY_PROFILE_NAME, userInfo.name).apply()
        prefs.edit().putString(Constants.KEY_PROFILE_INFO, userInfo.info).apply()
    }

    fun loadUserProfile(): UserInfo {
        return UserInfo(
            prefs.getString(Constants.KEY_PROFILE_IMAGE, Constants.DEFAULT_STRING)?.toUri() ?: Uri.EMPTY,
            prefs.getString(Constants.KEY_PROFILE_NAME, Constants.DEFAULT_STRING) ?: "",
            prefs.getString(Constants.KEY_PROFILE_INFO, Constants.DEFAULT_STRING)?: ""
        )
    }
}