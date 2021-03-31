package com.ks.hookdemo.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import android.widget.LinearLayout


class MyFragmentLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {



    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        Log.d("wyz", "MyFragmentLayout  draw")
    }

    override fun dispatchDraw(canvas: Canvas) {
//        canvas.restore()
        super.dispatchDraw(canvas)
        Log.d("wyz", "MyFragmentLayout  dispatchDraw")
    }
}