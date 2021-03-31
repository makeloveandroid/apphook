package com.ks.hookdemo.ui.dashboard

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.ks.hookdemo.R


class DashboardFragment : Fragment() {


    private lateinit var btn: TextView

    private lateinit var rootView: View
    private lateinit var tv: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        tv = view.findViewById<TextView>(R.id.text_dashboard)
        btn = view.findViewById<TextView>(R.id.tv_btn)

        btn.setOnClickListener(this::change)




    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun change(view: View) {
//        val transition = ChangeClipBounds()
//        transition.interpolator = BounceInterpolator()
        val fade = Fade()
        val slide = Slide()
        val set = TransitionSet()
        set.addTransition(fade).addTransition(slide).ordering = TransitionSet.ORDERING_TOGETHER
        TransitionManager.beginDelayedTransition(rootView as ViewGroup, set)
//        val width = tv.getWidth();
//        val height = tv.getHeight();
//        val gap = 140;
//        val rect =  Rect(0, gap, width, height - gap);
//        if (rect == tv.clipBounds) {
//            tv.clipBounds = null;
//        } else {
//            tv.clipBounds = rect;
//        }

        tv.isVisible = !tv.isVisible



    }


}
