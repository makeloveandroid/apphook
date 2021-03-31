package com.ks.core.action

import andhook.lib.XC_MethodHook
import andhook.lib.XposedBridge
import andhook.lib.XposedHelpers
import com.ks.core.net.data.JsonConvertor
import com.ks.core.net.data.WebSocketManager
import com.ks.core.util.ReflectUtils

abstract class BaseAction<IN, OUT> {
    data class ActionData<T>(val action: String, val data: T, val time: Long)

    abstract fun hook(classLoader: ClassLoader)

    abstract fun parsingData(input: IN): OUT

    fun send(result: OUT, actionName: String = getActionName()) {
        val data = JsonConvertor.getInstance()
            .toJson(ActionData(actionName, result, System.currentTimeMillis()))
        WebSocketManager.sendMsg(data)
    }

    private fun getActionName(): String {
        return javaClass.simpleName
    }
}

interface BaseHook {
    fun execute()
}

class MethodHook : BaseHook {
    var className = ""
    var clzz: Class<*>? = null
    var methodName = ""
    var parameterTypes: Array<out Any>
    var isConstructor = false
    var isHookAll = false
    var classLoader: ClassLoader?
    val beforeHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit
    val afterHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit

    constructor(builder: Builder) {
        this.className = builder.className
        this.methodName = builder.methodName
        this.parameterTypes = builder.parameterTypes
        this.isConstructor = builder.isConstructor
        this.isHookAll = builder.isHookAll
        this.classLoader = builder.classLoader
        this.beforeHookedMethod = builder.beforeHookedMethod
        this.afterHookedMethod = builder.afterHookedMethod
        this.clzz = builder.clzz
    }

    override fun execute() {
        val hookClzz = if (className.isNotEmpty()) {
            ReflectUtils.findClass(classLoader!!, className)
        } else {
            clzz
        }
        val parameterTypesAndCallback = arrayOfNulls<Any>(parameterTypes.size + 1)
        parameterTypes.forEachIndexed { index, any ->
            parameterTypesAndCallback[index] = any
        }
        parameterTypesAndCallback[parameterTypes.size] = object :
            XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                super.beforeHookedMethod(param)
                try {
                    param?.let(beforeHookedMethod)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
                try {
                    param?.let(afterHookedMethod)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        if (isConstructor) {
            hookConstructor(hookClzz, parameterTypesAndCallback)
        } else {
            hoomMethod(hookClzz, parameterTypesAndCallback)
        }
    }

    private fun hoomMethod(
        clzz: Class<*>?,
        parameterTypesAndCallback: Array<Any?>
    ) {
        if (isHookAll) {
            XposedBridge.hookAllMethods(
                clzz,
                methodName,
                parameterTypesAndCallback[0] as XC_MethodHook
            )
        } else {
            XposedHelpers.findAndHookMethod(
                clzz,
                methodName,
                *parameterTypesAndCallback
            )
        }
    }

    private fun hookConstructor(
        clzz: Class<*>?,
        parameterTypesAndCallback: Array<Any?>
    ) {
        if (isHookAll) {
            XposedBridge.hookAllConstructors(
                clzz,
                parameterTypesAndCallback[0] as XC_MethodHook
            )
        } else {
            XposedHelpers.findAndHookConstructor(clzz, *parameterTypesAndCallback)
        }
    }

    open class Builder {
        var className = ""
        var methodName = ""
        var parameterTypes: Array<out Any> = arrayOf()
        var isConstructor = false
        var isHookAll = false
        var classLoader: ClassLoader? = null
        var beforeHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit = {}
        var afterHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit = {}
        var clzz: Class<*>? = null

        fun classLoader(classLoader: ClassLoader): Builder {
            this.classLoader = classLoader
            return this
        }

        fun setClass(clzz: Class<*>?): Builder {
            this.clzz = clzz
            return this
        }

        fun className(className: String): Builder {
            this.className = className
            return this
        }

        fun methodName(methodName: String): Builder {
            this.methodName = methodName
            return this
        }

        fun parameterTypes(vararg parameterTypes: Any): Builder {
            this.parameterTypes = parameterTypes
            return this
        }

        fun isConstructor(isConstructor: Boolean): Builder {
            this.isConstructor = isConstructor
            return this
        }

        fun isHookAll(isHookAll: Boolean): Builder {
            this.isHookAll = isHookAll
            return this
        }

        fun beforeHookedMethod(beforeHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit = {}): Builder {
            this.beforeHookedMethod = beforeHookedMethod
            return this
        }

        fun afterHookedMethod(afterHookedMethod: (param: XC_MethodHook.MethodHookParam) -> Unit = {}): Builder {
            this.afterHookedMethod = afterHookedMethod
            return this
        }

        fun build(): MethodHook {
            return MethodHook(this)
        }
    }
}


class CallMethod : BaseHook {
    var className = ""
    var clzz: Class<*>? = null
    var methodName = ""
    var parameterTypes: Array<out Any>
    var classLoader: ClassLoader? = null

    constructor(builder: Builder) {
        this.className = builder.className
        this.methodName = builder.methodName
        this.parameterTypes = builder.parameterTypes
        this.classLoader = builder.classLoader
        this.clzz = builder.clzz
    }

    override fun execute() {
    }

    open class Builder {
        var className = ""
        var methodName = ""
        var parameterTypes: Array<out Any> = arrayOf()
        var classLoader: ClassLoader? = null
        var clzz: Class<*>? = null

        fun setClass(clzz: Class<*>?): Builder {
            this.clzz = clzz
            return this
        }

        fun className(className: String): Builder {
            this.className = className
            return this
        }

        fun methodName(methodName: String): Builder {
            this.methodName = methodName
            return this
        }

        fun parameterTypes(vararg parameterTypes: Any): Builder {
            this.parameterTypes = parameterTypes
            return this
        }

        fun build(): CallMethod {
            return CallMethod(this)
        }
    }
}