package com.ks.hookdemo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView

@SuppressLint("AppCompatCustomView")
class MyImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ImageView(context, attrs, defStyleAttr) {
    val paint = Paint()

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("wyz", "高度:${measuredHeight}")
        paint.shader = LinearGradient(
            0f, (measuredHeight - 150).toFloat(), 0f, measuredHeight.toFloat(),
            intArrayOf(0x00000000, 0x80000000.toInt()), null, Shader.TileMode.CLAMP
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d("wyz", "高度:${height}")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("wyz","height:${measuredHeight}")
        canvas.drawRect(
            0f,
            (measuredHeight - 150).toFloat(),
            width.toFloat(),
            height.toFloat(),
            paint
        )
    }

}