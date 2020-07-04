package com.ll.apt_library;

/**
 * @Auther kylin
 * @Data 2020/7/4 - 16:34
 * @Package com.ll.apt_library
 * @Description
 */
public interface IViewBind<T> {
//    public void inject(MainActivity target, Object source) {
//        if (source instanceof android.app.Activity) {
//            target.textView2 = ((android.app.Activity) source).findViewById(2131230920);
//        } else {
//            target.textView2 = ((android.view.View) source).findViewById(2131230920);
//        }
//    }
    void inject(T target, Object source);
}
