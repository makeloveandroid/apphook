package com.ks.core.layoutborder

import android.app.Activity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils

object LayoutScalpelManager {

    private fun resolveScalpelActivity(activity: Activity?) {
        if (activity == null) {
            return
        }
        val window = activity.window ?: return
        val appContentView: ViewGroup
        appContentView = window.decorView as ViewGroup
        if (appContentView == null) {
            ToastUtils.showShort("当前根布局功能不支持")
            return
        }
        if (appContentView.toString().contains("SwipeBackLayout")) {
            ToastUtils.showLong("普通模式下布局层级功能暂不支持以SwipeBackLayout为根布局")
            return
        }

        //将所有控件放入到ScalpelFrameLayout中
       val scalpelFrameLayout = ScalpelFrameLayout(appContentView.context)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        while (appContentView.childCount != 0) {
            val child = appContentView.getChildAt(0)
            if (child is ScalpelFrameLayout) {
                val isCheck = child.isLayerInteractionEnabled
                child.isLayerInteractionEnabled = !isCheck
                child.requestLayout()
                return
            }
            appContentView.removeView(child)
            scalpelFrameLayout.addView(child)
        }
        val isCheck = scalpelFrameLayout.isLayerInteractionEnabled
        scalpelFrameLayout.isLayerInteractionEnabled = !isCheck
        scalpelFrameLayout.layoutParams = params
        appContentView.addView(scalpelFrameLayout)
    }


    fun start() {
        val activity = ActivityUtils.getTopActivity() ?: return
        activity.runOnUiThread {
            resolveScalpelActivity(activity)
        }
    }

}