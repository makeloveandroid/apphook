package com.ks.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.webkit.WebView
import com.facebook.stetho.Stetho
import com.ks.core.util.LifecycleListenerUtil

object InitManager : Application.ActivityLifecycleCallbacks {
    fun init(application: Application) {
        try {
            //注册全局的activity生命周期回调
            application.registerActivityLifecycleCallbacks(this)
            Stetho.initializeWithDefaults(application)
            WebView.setWebContentsDebuggingEnabled(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityPaused(activity: Activity) {
        for (listener in LifecycleListenerUtil.LIFECYCLE_LISTENERS) {
            listener.onActivityPaused(activity)
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
        for (listener in LifecycleListenerUtil.LIFECYCLE_LISTENERS) {
            listener.onActivityResumed(activity)
        }
    }
}