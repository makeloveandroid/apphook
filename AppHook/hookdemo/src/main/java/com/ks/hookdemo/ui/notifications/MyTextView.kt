package com.ks.hookdemo.ui.notifications

import android.content.Context
import android.util.AttributeSet
import android.util.Log

class MyTextView constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("wyz", "MyTextView  onMeasure")
    }

}