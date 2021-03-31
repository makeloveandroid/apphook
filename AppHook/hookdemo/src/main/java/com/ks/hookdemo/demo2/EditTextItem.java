package com.ks.hookdemo.demo2;

import androidx.annotation.IntDef;

import com.ks.hookdemo.demo.Element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_HEIGHT;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_PADDING_BOTTOM;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_PADDING_LEFT;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_PADDING_RIGHT;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_PADDING_TOP;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_TEXT;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_TEXT_COLOR;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_TEXT_SIZE;
import static com.ks.hookdemo.demo2.EditTextItem.Type.TYPE_WIDTH;


public class EditTextItem extends ElementItem {

    private @Type
    int type;
    private String detail;

    public EditTextItem(String name, Element element, @Type int type, String detail) {
        super(name, element);
        this.type = type;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public int getType() {
        return type;
    }

    @IntDef({
            TYPE_TEXT,
            TYPE_TEXT_SIZE,
            TYPE_TEXT_COLOR,
            TYPE_WIDTH,
            TYPE_HEIGHT,
            TYPE_PADDING_LEFT,
            TYPE_PADDING_RIGHT,
            TYPE_PADDING_TOP,
            TYPE_PADDING_BOTTOM,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_TEXT = 1;
        int TYPE_TEXT_SIZE = 2;
        int TYPE_TEXT_COLOR = 3;
        int TYPE_WIDTH = 4;
        int TYPE_HEIGHT = 5;
        int TYPE_PADDING_LEFT = 6;
        int TYPE_PADDING_RIGHT = 7;
        int TYPE_PADDING_TOP = 8;
        int TYPE_PADDING_BOTTOM = 9;
    }
}
