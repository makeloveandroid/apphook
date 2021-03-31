package com.ks.hookdemo.demo

import android.app.Activity

object ActivityTool {
    @JvmStatic
    var activity: Activity? = null

    fun setAc(activity: Activity) {
        this.activity = activity
    }

}