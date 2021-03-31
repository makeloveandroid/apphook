package com.ks.hookdemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import com.ks.hookdemo.demo.DimenUtil.dip2px

/**
 * 带一个阴影的ImageView
 */
@SuppressLint("AppCompatCustomView")
class ShadowKuaiImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ImageView(context, attrs, defStyle) {
    private val DEFALUT_START_COLOR = 0x00000000
    private val DEFALUT_END_COLOR = 0x80000000.toInt()

    private var mViewWidth: Int = 0
    private var mViewHeight: Int = 0
    val mShadowPaint = Paint()
    private var mGravity = Gravity.BOTTOM

    /**
     * 阴影开始颜色
     */
    private var mStartColor: Int = DEFALUT_START_COLOR


    /**
     * 阴影结束颜色
     */
    private var mEndColor: Int = DEFALUT_END_COLOR

    private var mShadowWidth: Float = dip2px(30f).toFloat()
    private var mShadowHeight = dip2px(30f).toFloat()

    /**
     * 绘制区域
     */
    private val rectF = RectF()

    init {
        mShadowPaint.style = Paint.Style.FILL
        if (attrs != null) {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.ShadowKuaiImageView)
            mStartColor =
                typedArray.getColor(
                    R.styleable.ShadowKuaiImageView_kwaiShadowStartColor,
                    DEFALUT_START_COLOR
                )
            mEndColor =
                typedArray.getColor(
                    R.styleable.ShadowKuaiImageView_kwaiShadowEndColor,
                    DEFALUT_END_COLOR
                )

            mGravity =
                typedArray.getInt(
                    R.styleable.ShadowKuaiImageView_kuaiShadowlayoutGravity,
                    Gravity.BOTTOM
                )

            mShadowWidth =
                typedArray.getDimension(
                    R.styleable.ShadowKuaiImageView_kwaiShadowWidth,
                    0f
                )

            mShadowHeight =
                typedArray.getDimension(
                    R.styleable.ShadowKuaiImageView_kwaiShadowHeight,
                    0f
                )
            typedArray.recycle();
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mViewHeight = measuredHeight
        mViewWidth = measuredWidth

        if (mGravity == Gravity.LEFT) {
            rectF.left = 0f
            rectF.right = mShadowWidth
            rectF.bottom = mViewHeight.toFloat()
            rectF.top = 0f
        } else if (mGravity == Gravity.RIGHT) {
            rectF.left = mViewWidth - mShadowWidth
            rectF.right = mViewWidth.toFloat()
            rectF.bottom = mViewHeight.toFloat()
            rectF.top = 0f
        } else if (mGravity == Gravity.TOP) {
            rectF.left = 0f
            rectF.right = mViewWidth.toFloat()
            rectF.bottom = mShadowHeight
            rectF.top = 0f
        } else {
            rectF.left = 0f
            rectF.top = mViewHeight - mShadowHeight
            rectF.right = mViewWidth.toFloat()
            rectF.bottom = mViewHeight.toFloat()
        }
        mShadowPaint.shader = LinearGradient(
            rectF.left, rectF.top, 0f, rectF.bottom,
            intArrayOf(mStartColor, mEndColor), null, Shader.TileMode
                .CLAMP
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(rectF, mShadowPaint)
    }

}