package com.ks.core.action

import android.util.Log
import com.ks.core.util.ReflectUtils


class FragmentData(
    var type: String,
    val className: String,
    val activityName: String,
    var event: String
)

class FragmentCreateAction : BaseAction<Any, FragmentData>() {
    override fun hook(classLoader: ClassLoader) {
        // 区分Fragment
        val xFragment = ReflectUtils.findClass(classLoader, "androidx.fragment.app.Fragment")
        if (xFragment != null) {
            hookFragment("androidx.fragment.app.Fragment", xFragment)
        }
        val fragment = ReflectUtils.findClass(classLoader, "android.app.Fragment")
        if (fragment != null) {
            hookFragment("android.app.Fragment", fragment)
        }

        val v4fragment = ReflectUtils.findClass(classLoader, "android.support.v4.app.Fragment")
        if (v4fragment != null) {
            hookFragment("android.app.Fragment", v4fragment)
        }
    }

    private fun hookFragment(type: String, fragment: Class<*>) {
        Log.d("wyz", "hook::$type")
        MethodHook
            .Builder()
            .setClass(fragment)
            .methodName("onViewCreated")
            .isHookAll(true)
            .afterHookedMethod {
                val input = parsingData(it.thisObject)
                input.type = type
                input.event = "onViewCreated"
                send(input, "FragmentOnViewCreated")
            }
            .build()
            .execute()


        MethodHook
            .Builder()
            .setClass(fragment)
            .methodName("setUserVisibleHint")
            .isHookAll(true)
            .afterHookedMethod {
                val isUserVisibleHint = it.args[0] as Boolean
                if (isUserVisibleHint) {
                    val input = parsingData(it.thisObject)
                    input.type = type
                    input.event = "setUserVisibleHint"
                    send(input, "FragmentUserVisibleHint")
                }
            }
            .build()
            .execute()


        MethodHook
            .Builder()
            .setClass(fragment)
            .methodName("onResume")
            .isHookAll(true)
            .afterHookedMethod {
                val input = parsingData(it.thisObject)
                input.type = type
                input.event = "onResume"
                send(input, "FragmentOnResume")
            }
            .build()
            .execute()
    }

    override fun parsingData(input: Any): FragmentData {
        val activity = ReflectUtils.invokeMethod(input, "getActivity", null)
        return FragmentData("", input::class.java.name, activity.toString(), "")
    }
}