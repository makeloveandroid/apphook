package com.ks.core

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.blankj.utilcode.util.Utils
import com.ks.core.action.MethodHook
import com.ks.core.callaction.CallActionManager
import com.ks.core.net.data.WebSocketManager
import com.ks.core.util.LocationHookUtils
import com.ks.core.util.SharedPreferencesUtils
import java.io.File

class HookReceiver(val app: Application) :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent) {
        val pkg = intent.getStringExtra("HOOK_PKG")
        log( "收到广播:${pkg}")
        if (pkg == app.packageName) {
            log("开始hook:${pkg}")
            Core.appHook(app)
        }
    }
}
object Core  {
    val dir =
        Environment.getExternalStorageDirectory().absolutePath + "/Android/data/com.ks.apphook/cache/apphook/"
    const val ACTION = "com.ks.apphook.hookreceiver"

    private var isHook = false

    /**
     * xposedhook
     */
    fun xposedHook(classLoader: ClassLoader) {
        if (isHook) return
        isHook = true
        MethodHook
            .Builder()
            .setClass(Application::class.java)
            .methodName("onCreate")
            .afterHookedMethod {
                val app = it.thisObject as Application
                val file = File("${dir}${app.packageName}")
                Log.d(
                    "wyz",
                    "processName  " + app.packageName + " " + file.absolutePath + "  " + file.exists()
                )
                if (file.exists()) {
                    log("hook${app.packageName}")
                    appHook(app)
                }
            }
            .build()
            .execute()
    }

    /**
     * 应用级hook
     */
    fun appHook(application: Application) {
        val context = application.applicationContext
        val app = application
        val classLoader = application.classLoader
        Utils.init(app)
        SharedPreferencesUtils.init(context)
        CallActionManager.init()
        WebSocketManager.onOpen(context)
        LocationHookUtils.startHook(classLoader)
//        DexHelper.loadDex(context)
        InitManager.init(app)
        HookManager.register()
        HookManager.hookAll(classLoader)
        log("hook完成!!")
    }

    fun isHook(context: Context): Boolean {
        val packageName = context.packageName
        val uri: Uri = Uri.parse("content://com.ks.apphook")
        val cursor = context.contentResolver.query(uri, null, "PKG=?", arrayOf(packageName), null)
        val count = cursor?.count ?: 0
        cursor?.close()
        log("hook开始判断${packageName}  $count")
        return count > 0
    }
}