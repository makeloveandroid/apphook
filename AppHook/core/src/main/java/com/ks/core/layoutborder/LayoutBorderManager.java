package com.ks.core.layoutborder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ActivityUtils;
import com.ks.core.util.LifecycleListenerUtil;

/**
 * Created by wanglikun on 2019/1/9
 */
public class LayoutBorderManager {
    private boolean isRunning;

    private ViewBorderFrameLayout mViewBorderFrameLayout;

    private LifecycleListenerUtil.LifecycleListener mLifecycleListener = new LifecycleListenerUtil.LifecycleListener() {
        @Override
        public void onActivityResumed(Activity activity) {
            //UIUtils.getDokitAppContentView(activity).setId(R.id.dokit_app_contentview_id);
            resolveActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onFragmentAttached(Fragment f) {

        }

        @Override
        public void onFragmentDetached(Fragment f) {
            if (mViewBorderFrameLayout != null) {
                mViewBorderFrameLayout = null;
            }
        }
    };

    private void resolveActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        final ViewGroup root = (ViewGroup) window.getDecorView();
        if (root == null) {
            return;
        }
        mViewBorderFrameLayout = new ViewBorderFrameLayout(root.getContext());
        while (root.getChildCount() != 0) {
            View child = root.getChildAt(0);
            if (child instanceof ViewBorderFrameLayout) {
                mViewBorderFrameLayout = (ViewBorderFrameLayout) child;
                mViewBorderFrameLayout.requestLayout();
                return;
            }
            root.removeView(child);
            mViewBorderFrameLayout.addView(child);
        }
        root.addView(mViewBorderFrameLayout);
    }

    private static class Holder {
        private static LayoutBorderManager INSTANCE = new LayoutBorderManager();
    }

    private LayoutBorderManager() {
    }

    public static LayoutBorderManager getInstance() {
        return Holder.INSTANCE;
    }

    public void startBorder() {
        final Activity topActivity = ActivityUtils.getTopActivity();
        topActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                LayoutBorderConfig.setLayoutBorderOpen(true);
                resolveActivity(topActivity);
                LifecycleListenerUtil.registerListener(mLifecycleListener);
            }
        });
    }

    public void stopBorder() {
        final Activity topActivity = ActivityUtils.getTopActivity();
        topActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutBorderConfig.setLayoutBorderOpen(false);
                isRunning = false;
                if (mViewBorderFrameLayout != null) {
                    mViewBorderFrameLayout.requestLayout();
                }
                mViewBorderFrameLayout = null;
                LifecycleListenerUtil.unRegisterListener(mLifecycleListener);
            }
        });

    }

    public boolean isRunning() {
        return isRunning;
    }
}