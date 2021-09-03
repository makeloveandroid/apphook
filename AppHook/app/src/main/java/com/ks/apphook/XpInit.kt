package com.ks.apphook

import android.os.Environment
import android.util.Log
import com.ks.core.Core
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.io.File

//class MyContextScope(context: CoroutineContext) : CoroutineScope {
//    override val coroutineContext: CoroutineContext = context
//}
//
//val mainScope by lazy {
//    val dispatcherFactoryClzz = Class.forName("kotlinx.coroutines.android.AndroidDispatcherFactory")
//    val factoryObj = dispatcherFactoryClzz.newInstance()
//    val method = dispatcherFactoryClzz.getMethod("createDispatcher", List::class.java)
//    val invoke = method.invoke(factoryObj, mutableListOf<Any>()) as CoroutineContext
//    val myContextScope = MyContextScope(SupervisorJob() + invoke)
//    myContextScope
//}

class XpInit : IXposedHookLoadPackage {
    val dir =
        Environment.getExternalStorageDirectory().absolutePath + "/Android/data/com.ks.apphook/cache/apphook/"
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val file = File("${dir}${lpparam.packageName}")
        Log.d(
            "wyz",
            "processName  " + lpparam.processName + " " + file.absolutePath + "  " + file.exists()
        )
        if (file.exists()){
            Core.xposedHook(lpparam.classLoader)
        }
    }
}