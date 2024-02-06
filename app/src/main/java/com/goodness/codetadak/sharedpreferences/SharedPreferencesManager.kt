package com.goodness.codetadak.sharedpreferences

import android.content.Context
import com.goodness.codetadak.Constants
import com.goodness.codetadak.api.responses.VideosResponse
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

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

}