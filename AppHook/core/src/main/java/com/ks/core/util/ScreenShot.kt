package com.ks.core.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.View
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


object ScreenShot {
    // 获取指定Activity的截屏，保存到png文件
    private fun takeScreenShot(activity: Activity): Bitmap {
        // View是你需要截图的View
        val view: View = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val b1: Bitmap = view.getDrawingCache()

        // 获取状态栏高度
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight: Int = frame.top

        // 获取屏幕长和高
        val width = activity.windowManager.defaultDisplay.width
        val height = activity.windowManager.defaultDisplay
            .height
        // 去掉标题栏
        val b = Bitmap.createBitmap(
            b1, 0, statusBarHeight, width, height
                    - statusBarHeight
        )
        view.destroyDrawingCache()
        return b
    }


    // 保存到sdcard
    private fun savePic(b: Bitmap, strFileName: String) {
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(strFileName)
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    /**
     * 把Bitmap转Byte
     */
    private fun bitmap2Bytes(bm: Bitmap): ByteArray? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    // 程序入口
    fun shoot(a: Activity): ByteArray? {
        return bitmap2Bytes(takeScreenShot(a))
    }
}
