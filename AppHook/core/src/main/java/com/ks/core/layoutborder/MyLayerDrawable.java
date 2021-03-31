package com.ks.core.layoutborder;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import androidx.annotation.NonNull;

public class MyLayerDrawable extends LayerDrawable {
    private Drawable[] layers;

    public MyLayerDrawable(@NonNull Drawable[] layers) {
        super(layers);
        this.layers = layers;
    }

    @Override
    public ConstantState getConstantState() {
        if (layers.length == 2) {
            // 存在背景
            return layers[0].getConstantState();
        }
        return super.getConstantState();
    }
}
