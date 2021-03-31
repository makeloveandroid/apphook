package com.ks.hookdemo.demo2.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;


public class JavaDialogAttrs {
    @SuppressLint("ResourceType")
    public static View buildJavaDialogAttrs(Context context) {
        LinearLayout layout_root = new LinearLayout(context);
        LinearLayout linearlayout_0 = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams_0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        linearlayout_0.setBackgroundColor(0xc0000000);
        linearlayout_0.setFocusable(true);
        linearlayout_0.setFocusableInTouchMode(true);
        linearlayout_0.setOrientation(LinearLayout.VERTICAL);
        ListView list = new ListView(context);
        LinearLayout.LayoutParams layoutParams_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        list.setId(10001);
        linearlayout_0.addView(list,layoutParams_1);
        layout_root.addView(linearlayout_0,layoutParams_0);
        return layout_root;
    }
}
