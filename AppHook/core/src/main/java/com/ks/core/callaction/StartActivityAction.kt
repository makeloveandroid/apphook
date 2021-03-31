package com.ks.core.callaction

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.gson.Gson
import com.ks.core.util.ReflectUtils
import com.ks.core.util.SafetyUriCalls

class StartActivityAction : BaseCallAction {
    override fun getActionName(): String {
        return javaClass.simpleName
    }

    override fun execute(context: Context, data: String) {
        val parameter =
            Gson().fromJson(data, StartActivityParameter::class.java)
        if (!parameter.isSchema) {
            Intent().apply {
                component = ComponentName(context.packageName, parameter.activityName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }.apply {
                val p = parameter.buildIntentParameter();
                p?.keys?.forEach { key ->
                    val value = p!![key];
                    putExtra(key, value)
                }
            }.let(context::startActivity)
        } else {
            try {
                val classLoader = context.classLoader
                val uriIntentFactory =
                    ReflectUtils.findClass(classLoader, "com.kwai.framework.router.UriIntentFactory")
                val uriObj = ReflectUtils.invokeStaticMethod(
                    classLoader,
                    "com.yxcorp.utility.singleton.Singleton",
                    "get",
                    arrayOf(Class::class.java),
                    uriIntentFactory
                )
                val uri = SafetyUriCalls.parseUriFromString(parameter.activityName)
                val intent = ReflectUtils.invokeMethod(
                    uriObj, "createIntentWithAnyUri", arrayOf(
                        Context::class.java,
                        Uri::class.java, Boolean::class.java, Boolean::class.java
                    ), context, uri, true, false
                ) as? Intent
                if (intent!=null){
                    intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            } catch (e: Exception) {
            }

        }
    }
}