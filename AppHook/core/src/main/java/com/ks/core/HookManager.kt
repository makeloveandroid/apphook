package com.ks.core

import com.ks.core.action.*

object HookManager {
    private var isRegister = false

    private val hooks = mutableListOf<BaseAction<*, *>>()


    fun hookAll(classLoader: ClassLoader) {
        hooks.forEach {
            it.hook(classLoader)
        }
    }

    /**
     * 后期是否可以考虑AOP注入
     */
    fun register() {
        if (!isRegister) {
            isRegister = true
            hooks.add(ActivityAction())
            hooks.add(ViewClickEventAction())
            hooks.add(ViewLongClickEventAction())
            hooks.add(FragmentCreateAction())
            hooks.add(StartActivityAction())
//            hooks.add(OkHttpAction())
        }
    }
}