package com.ks.core.action

import android.app.Activity
import android.os.Bundle

data class ClassData(val value: String, val type: String)

data class ActivityOnCreateData(val activityName: String, val intentData: Map<String, ClassData>)

class ActivityAction : BaseAction<Activity, ActivityOnCreateData>() {
    override fun hook(classLoader: ClassLoader) {
        MethodHook
            .Builder()
            .setClass(Activity::class.java)
            .methodName("onCreate")
            .parameterTypes(Bundle::class.java)
            .afterHookedMethod {
                val activity = it.thisObject as Activity
                send(parsingData(activity))
            }
            .build()
            .execute()
    }

    override fun parsingData(input: Activity): ActivityOnCreateData {
        val intent = input.intent
        val bundle = intent.extras
        val activityName = input::class.java.name
        if (bundle == null) {
            return ActivityOnCreateData(activityName, mutableMapOf())
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
                    intentData[keyStr] = ClassData(o?.toString() ?: "", type)
                }
            } else {
                val o = bundle.get(key)
                var type = ""
                if (o != null) {
                    type = o.javaClass.name
                }
                intentData[key] = ClassData(o?.toString() ?: "", type)
            }
        }
        return ActivityOnCreateData(activityName, intentData)
    }

}