package com.ll.apt_library;

import android.app.Activity;

/**
 * @Auther kylin
 * @Data 2020/7/4 - 20:37
 * @Package com.ll.apt_library
 * @Description
 */
public class KylinKnife {
    private static final String TAG = "KylinKnife";
    public static final String PROXY = "_ViewBinding";

    public static void bind(Activity activity){
        IViewBind mBind = findProxyClass(activity);
        if (mBind != null){
            mBind.inject(activity, activity);
        }
    }

    private static IViewBind findProxyClass(Object object){
        String proxyClassName = object.getClass().getName() + PROXY;
        Class<?> proxyClass = null;
        try {
            proxyClass = Class.forName(proxyClassName);
            return (IViewBind)proxyClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
