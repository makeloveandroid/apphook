package com.ks.core.callaction

import android.content.Context
import com.ks.core.layoutborder.LayoutScalpelManager


class LayoutScalpelAction : BaseCallAction {
    override fun getActionName(): String {
        return "LayoutScalpelAction"
    }

    override fun execute(context: Context, data: String) {
        LayoutScalpelManager.start()
    }

}