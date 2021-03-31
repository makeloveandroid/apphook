package com.ks.core.callaction

import android.content.Context
import android.content.Intent
import android.os.Process

class ReStartAppAction : BaseCallAction {
    override fun getActionName(): String {
        return "ReStartAppAction"
    }

    override fun execute(context: Context, data: String) {
        //启动页
        val packageManager = context.packageManager ?: return
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
        Process.killProcess(Process.myPid())
    }
}