package com.ks.hookdemo.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.Gravity.BOTTOM;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class TransparentActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "extra_type";

    private ViewGroup vContainer;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            finish();
            return;
        }

        Util.setStatusBarColor(getWindow(), Color.TRANSPARENT);
        Util.enableFullscreen(getWindow());
        vContainer = new FrameLayout(getApplicationContext());

        setContentView(vContainer);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.gravity = BOTTOM;
        RelativePositionLayout relativePositionLayout = new RelativePositionLayout(getApplicationContext());
        vContainer.addView(relativePositionLayout, params);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void dismissAttrsDialog() {

    }

    @IntDef({
            Type.TYPE_UNKNOWN,
            Type.TYPE_EDIT_ATTR,
            Type.TYPE_SHOW_GRIDDING,
            Type.TYPE_RELATIVE_POSITION,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_UNKNOWN = -1;
        int TYPE_EDIT_ATTR = 1;
        int TYPE_SHOW_GRIDDING = 2;
        int TYPE_RELATIVE_POSITION = 3;
    }
}
