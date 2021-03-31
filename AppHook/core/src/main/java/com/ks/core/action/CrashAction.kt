package com.ks.core.action

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer

data class CrashData(val timeMillis: Long, val data: String, var threadName: String = "")

class CrashAction : BaseAction<java.lang.Throwable, CrashData>() {
    override fun hook(classLoader: ClassLoader) {
        val c = ThreadGroup::class.java
        Log.d("wyz", "defaultUncaughtExceptionHandler:$c")
        MethodHook
            .Builder()
            .setClass(c)
            .methodName("uncaughtException")
            .parameterTypes(Thread::class.java, java.lang.Throwable::class.java)
            .afterHookedMethod {
                Log.d("wyz", "异常回调!")
                val throwable = it.args[1] as java.lang.Throwable
                val thread = it.args[0] as Thread

                sendCrash(throwable, thread)
            }
            .build()
            .execute()

        MethodHook
            .Builder()
            .setClass(Thread::class.java)
            .methodName("dispatchUncaughtException")
            .parameterTypes(java.lang.Throwable::class.java)
            .afterHookedMethod {
                Log.d("wyz", "异常回调!")
                val throwable = it.args[1] as java.lang.Throwable
                val thread = it.thisObject as Thread

                sendCrash(throwable, thread)
            }
            .build()
            .execute()

        MethodHook
            .Builder()
            .className("com.android.internal.os.RuntimeInit\$KillApplicationHandler")
            .classLoader(classLoader)
            .methodName("uncaughtException")
            .parameterTypes(Thread::class.java, java.lang.Throwable::class.java)
            .afterHookedMethod {
                Log.d("wyz", "异常回调!")
                val throwable = it.args[1] as java.lang.Throwable
                val thread = it.thisObject as Thread

                sendCrash(throwable, thread)
            }
            .build()
            .execute()

        Log.d("wyz", "hook异常成功!")
    }

    private fun sendCrash(throwable: java.lang.Throwable, thread: Thread) {
        Log.d("wyz", "收到了异常!!")
        val crashData = parsingData(throwable)
        crashData.threadName = thread.name
        send(crashData)
    }

    override fun parsingData(input: java.lang.Throwable): CrashData {
        return getCrashInfo(input)
    }

    private fun getCrashInfo(ex: java.lang.Throwable): CrashData {
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var exCause = ex.cause
        while (exCause != null) {
            exCause.printStackTrace(printWriter)
            exCause = exCause.cause
        }
        printWriter.close()
        val timeMillis = System.currentTimeMillis()
        val error = writer.toString()

        return CrashData(timeMillis, error)
    }

}