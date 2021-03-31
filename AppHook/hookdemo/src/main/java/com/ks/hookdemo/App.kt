package com.ks.hookdemo

import android.app.Application
import android.content.Context
import com.ks.core.Core

class App : Application() {

    companion object {
        var app: Application? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        Core.appHook(this)
    }
}