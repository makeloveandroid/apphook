package com.ks.core.callaction

import android.app.Activity
import android.content.Context
import android.util.Base64
import com.blankj.utilcode.util.ActivityUtils
import com.ks.core.action.BaseAction
import com.ks.core.util.ReflectUtils
import com.ks.core.util.ScreenShot

data class CutData(val activityName: String, val base64Byte: String)

class CutActivityAction : BaseCallAction, BaseAction<Activity, CutData>() {
    override fun getActionName(): String {
        return javaClass.simpleName
    }

    override fun execute(context: Context, data: String) {
        // Singleton.get(ActivityContextManager.class).getCurrentActivity()))
        try {
            val topActivity = ActivityUtils.getTopActivity()
            val cutData = topActivity?.let(::parsingData)
            if (cutData!=null){
                send(cutData)
            }
        } catch (e: Exception) {
        }
    }

    override fun hook(classLoader: ClassLoader) {

    }

    override fun parsingData(input: Activity): CutData {
        val byteArray = ScreenShot.shoot(input)
        return CutData(input.javaClass.name, Base64.encodeToString(byteArray, Base64.NO_WRAP))
    }
}