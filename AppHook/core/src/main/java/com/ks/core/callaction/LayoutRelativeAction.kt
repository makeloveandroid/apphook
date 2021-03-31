package com.ks.core.callaction

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.ks.core.ui.RelativePositionLayout
import com.ks.core.ui.Util


class LayoutRelativeAction : BaseCallAction {
    override fun getActionName(): String {
        return "LayoutRelativeAction"
    }

    override fun execute(context: Context, data: String) {
        val topActivity = ActivityUtils.getTopActivity()
        topActivity?.let(this::showRelativeHelperDialog)
    }

    private fun showRelativeHelperDialog(activity: Activity) {
        activity.runOnUiThread {
            FullDialog(activity).show()
        }
    }
}


@SuppressLint("ResourceType")
class FullDialog(context: Context) : Dialog(context, 3) {

    init {
        Util.setStatusBarColor(window!!, Color.TRANSPARENT)
        Util.enableFullscreen(window!!)
        val layoutParams: WindowManager.LayoutParams = window!!.attributes
        layoutParams.title = "FullDialog"
        window!!.setDimAmount(0.0f)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)

        initContentView()
    }

    private fun initContentView() {

        val vContainer = FrameLayout(context.applicationContext)
        vContainer.setBackgroundColor(Color.TRANSPARENT)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val relativePositionLayout =
            RelativePositionLayout(context.applicationContext)
        vContainer.addView(relativePositionLayout, params)
        // 追加辅助text
        val textView = TextView(context)
        textView.text = "当前处于相对位置查看中，按返回键退出!!"
        textView.setTextColor(Color.WHITE)
        val padding = dp2px(2f);
        textView.setPadding(padding, padding, padding, padding)
        textView.setBackgroundColor(Color.BLUE)
        textView.alpha = 0.3f
        textView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.BOTTOM or Gravity.LEFT
        }
        vContainer.addView(textView)
        setContentView(vContainer)
    }


}