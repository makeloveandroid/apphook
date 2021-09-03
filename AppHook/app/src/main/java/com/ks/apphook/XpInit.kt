package com.ks.apphook

import com.ks.core.Core
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

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
  override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
    if (lpparam.processName == lpparam.packageName) {
      Core.xposedHook(lpparam.classLoader)
    }
  }
}