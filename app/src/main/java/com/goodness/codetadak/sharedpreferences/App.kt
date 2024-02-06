package com.goodness.codetadak.sharedpreferences

import android.app.Application

class App : Application() {
    companion object {
        lateinit var prefs : SharedPreferencesManager
    }

    override fun onCreate() {
        prefs = SharedPreferencesManager(applicationContext)
        super.onCreate()
    }
}