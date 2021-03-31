package com.ks.apphook

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.ks.apphook.dao.DaoManager
import com.ks.core.Core
import com.ks.core.log


class HookReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val pkg = intent?.getStringExtra("PKG") ?: return
        DaoManager.init(context)
        log("检测是否拦截:${pkg}")
        if (DaoManager.haveHookApp(pkg)) {
            context.sendBroadcast(Intent(Core.ACTION).apply {
                putExtra("PKG", pkg)
                component = ComponentName(pkg, "com.ks.core.HookReceiver")
            })
        }
    }
}