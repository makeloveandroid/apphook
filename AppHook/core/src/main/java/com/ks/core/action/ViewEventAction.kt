package com.ks.core.action

import android.util.Log
import android.view.View
import com.ks.core.log
import com.ks.core.util.ReflectUtils

data class EventData(
    val type: String,
    val viewId: Int,
    val viewName: String,
    val eventName: String,
    val stack: String
)


abstract class ViewEventAction : BaseAction<View, EventData>() {
    override fun hook(classLoader: ClassLoader) {
        MethodHook
            .Builder()
            .setClass(View::class.java)
            .isHookAll(true)
            .methodName(getMethodName())
            .afterHookedMethod {
                log("当前点击了:${it}")
                send(parsingData(it.thisObject as View))
            }
            .build()
            .execute()
    }

    abstract fun getMethodName(): String

    override fun parsingData(input: View): EventData {
        val viewName = input.toString()
        val viewId = input.id
        val listenerInfo = ReflectUtils.readField(input, "mListenerInfo")
        val event = listenerInfo?.let {
            ReflectUtils.readField(it, getEventName())
        }
        val stack = Log.getStackTraceString(Throwable())
        return EventData(getEventType(), viewId, viewName, event.toString(), stack)
    }

    abstract fun getEventName(): String

    abstract fun getEventType(): String
}

class ViewClickEventAction : ViewEventAction() {
    override fun getMethodName(): String {
        return "performClick"
    }

    override fun getEventName(): String {
        return "mOnClickListener"
    }

    override fun getEventType(): String {
        return "Click"
    }
}

class ViewLongClickEventAction : ViewEventAction() {
    override fun getMethodName(): String {
        return "performLongClickInternal"
    }

    override fun getEventName(): String {
        return "mOnLongClickListener"
    }

    override fun getEventType(): String {
        return "LongClick"
    }

}