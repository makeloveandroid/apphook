package com.ks.core.callaction

import android.content.Context
import com.ks.core.layoutborder.LayoutBorderManager

data class LayoutBorderActionData(val isOpen: Boolean)

class LayoutBorderAction : BaseCallAction {
    override fun getActionName(): String {
        return "LayoutBorderAction"
    }

    override fun execute(context: Context, data: String) {
//        val parameter =
//            Gson().fromJson(data, LayoutBorderActionData::class.java)
        if (LayoutBorderManager.getInstance().isRunning) {
            LayoutBorderManager.getInstance().stopBorder()
        } else {
            LayoutBorderManager.getInstance().startBorder()
        }
    }

}