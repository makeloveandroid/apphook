package com.ks.core.action

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ks.core.log


data class StartActivityOnCreateData(
    val activityName: String,
    var startContext: String,
    val intentData: Map<String, ClassData>,
    var stack: String = ""
)

class StartActivityAction : BaseAction<Intent, StartActivityOnCreateData>() {
    override fun hook(classLoader: ClassLoader) {
        MethodHook
            .Builder()
            .setClass(Instrumentation::class.java)
            .methodName("execStartActivity")
            .isHookAll(true)
            .afterHookedMethod {
                log("activity启动:${it.args.size}")
                if (it.args.size == 7) {
                    val context = it.args[0] as Context
                    val intent = it.args[4] as Intent
                    val data = parsingData(intent)
                    data.startContext = context.javaClass.name
                    data.stack = Log.getStackTraceString(Throwable())
                    send(data)
                }
            }
            .build()
            .execute()

    }

    override fun parsingData(input: Intent): StartActivityOnCreateData {
        val bundle = input.extras
        var activityName = ""
        input.component?.apply {
            activityName = className
        }
        if (bundle == null) {
            return StartActivityOnCreateData(activityName, "", mutableMapOf())
        }
        val keySet = bundle.keySet()
        val intentData = mutableMapOf<String, ClassData>()
        for (key in keySet) {
            //自己的业务需要
            if (bundle.get(key) is Bundle) {
                val b = bundle.get(key) as Bundle?
                val keys = b!!.keySet()
                for (keyStr in keys) {
                    val o = b.get(keyStr)
                    var type = ""
                    if (o != null) {
                        type = o.javaClass.name
                    }
                    intentData[keyStr] = ClassData(o.toString(), type)
                }
            } else {
                val o = bundle.get(key)
                var type = ""
                if (o != null) {
                    type = o.javaClass.name
                }
                intentData[key] = ClassData(o.toString(), type)
            }
        }

        return StartActivityOnCreateData(activityName, "", intentData)
    }

}