package com.ks.hookdemo.ui.home

import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ks.hookdemo.R
import com.ks.hookdemo.demo.TransparentActivity
import com.ks.hookdemo.demo.Util
import com.ks.hookdemo.ui.notifications.MyTextView


fun toast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initView(root = root as ViewGroup)
        return root
    }

    private fun initView(root: ViewGroup) {
        val ff = root.findViewById<ViewGroup>(R.id.ff)


        root.findViewById<View>(R.id.click).setOnClickListener {
            val text = MyTextView(requireContext())
            text.text = "增加的View"
            ff.addView(text)
            text.layoutParams.height = 200
            Log.d("wyz", "结束")
        }

        root.findViewById<View>(R.id.longClick).setOnLongClickListener {

//            ActivityTool.activity = activity
//            val vContainer = FrameLayout(requireActivity().applicationContext)
//            vContainer.setBackgroundColor(Color.TRANSPARENT)
//            val params = FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            params.gravity = Gravity.BOTTOM
//            val relativePositionLayout =
//                RelativePositionLayout(requireActivity().applicationContext)
//            vContainer.addView(relativePositionLayout, params)
//            val mParams = WindowManager.LayoutParams()
//
//            mParams.format = PixelFormat.TRANSLUCENT // 支持透明
//
//            mParams.width = ViewGroup.LayoutParams.MATCH_PARENT //窗口的宽和高
//
//            mParams.height = ViewGroup.LayoutParams.MATCH_PARENT
//            mParams.title = "tran"
//
//            FullDialog(requireContext()).apply { setContentView(vContainer) }.show()
//
//
//
//
//            activity?.overridePendingTransition(0, 0)


            val intent = Intent(activity, TransparentActivity::class.java)
            activity?.startActivity(intent)
            activity?.overridePendingTransition(0, 0)

            startActivity(Intent().apply {

                component =
                    ComponentName(requireActivity().packageName, "com.ks.hookdemo.Main2Activity")
                putExtra("测试", "1")
                putExtra("测试2", 2)
                putExtra("HEHE", 3L)
            })
            true
        }

        activity
    }


    fun restartApp() {
        //启动页
        val applicationContext = activity?.applicationContext!!
        val packageManager = applicationContext.packageManager ?: return
        val intent = packageManager.getLaunchIntentForPackage(applicationContext.packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
        }
        Process.killProcess(Process.myPid())
    }
}

class FullDialog(context: Context) : Dialog(context, 3) {

    init {
        Util.setStatusBarColor(window!!, Color.TRANSPARENT)
        Util.enableFullscreen(window!!)

        val layoutParams: WindowManager.LayoutParams = window!!.attributes
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.title = "FullDialog"
        window!!.setDimAmount(0.0f)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
//        window!!.decorView.setPadding(0, 0, 0, 0);
//        layoutParams.horizontalMargin = 0F
//        layoutParams.verticalMargin = 0F
//        window!!.setDimAmount(0.0f)


    }


}