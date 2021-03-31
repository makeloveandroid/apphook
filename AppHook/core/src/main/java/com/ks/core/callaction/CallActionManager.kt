package com.ks.core.callaction

import android.content.Context
import com.ks.core.callaction.ks.TestApiAction

object CallActionManager {
    private val actions: MutableMap<String, in BaseCallAction> = mutableMapOf()

    fun addAction(action: String, baseCallAction: BaseCallAction) {
        actions[action] = baseCallAction
    }

    fun removeAction(action: String) {
        actions.remove(action)
    }

    fun execute(context: Context, action: String, data: String) {
        actions[action]?.apply {
            val action = this as BaseCallAction
            action?.execute(context, data)
        }
    }

    fun init() {
        val startActivityAction = StartActivityAction()
        addAction(startActivityAction.getActionName(), startActivityAction as BaseCallAction)

        val locationAction = LocationAction()
        addAction(locationAction.getActionName(), locationAction as BaseCallAction)

        val cutActivityAction = CutActivityAction()
        addAction(cutActivityAction.getActionName(), cutActivityAction as BaseCallAction)

        val reStartAppAction = ReStartAppAction()
        addAction(reStartAppAction.getActionName(), reStartAppAction as BaseCallAction)

        val layoutBorderAction = LayoutBorderAction()
        addAction(layoutBorderAction.getActionName(), layoutBorderAction as BaseCallAction)

        val layoutScalpelAction = LayoutScalpelAction()
        addAction(layoutScalpelAction.getActionName(), layoutScalpelAction as BaseCallAction)

        val layoutRelativeAction = LayoutRelativeAction()
        addAction(layoutRelativeAction.getActionName(), layoutRelativeAction as BaseCallAction)

        val testApiAction = TestApiAction()
        addAction(testApiAction.getActionName(), testApiAction as BaseCallAction)
    }
}