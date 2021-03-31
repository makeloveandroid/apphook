package com.ks.hookdemo.demo2;

import com.ks.hookdemo.demo.Element;

public class ElementItem extends TitleItem {

    private Element element;

    public ElementItem(String name, Element element) {
        super(name);
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
