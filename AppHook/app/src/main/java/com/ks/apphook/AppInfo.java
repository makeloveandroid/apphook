package com.ks.apphook;

import android.graphics.drawable.Drawable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AppInfo {
    public String name;
    @Transient
    public Drawable icon;
    public String pkg;
    @Transient
    public String path;
    @Transient
    public boolean isHook;

    public AppInfo(String name, Drawable icon, String pkg, String path, boolean isHook) {
        this.name = name;
        this.icon = icon;
        this.pkg = pkg;
        this.path = path;
        this.isHook = isHook;
    }

    @Generated(hash = 1919745129)
    public AppInfo(String name, String pkg) {
        this.name = name;
        this.pkg = pkg;
    }

    @Generated(hash = 1656151854)
    public AppInfo() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkg() {
        return this.pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
}
