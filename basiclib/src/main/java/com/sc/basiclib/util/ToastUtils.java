package com.sc.basiclib.util;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sc.basiclib.IApp;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
public final class ToastUtils {

    private static Application mContext;

    private static Application getContext() {
        if (null == mContext) {
            // 使用依赖查找的方式发现服务
            IApp iApp = ARouter.getInstance().navigation(IApp.class);
//            IApp app = (IApp) ARouter.getInstance().build("/service/app").navigation();
            mContext = iApp.getApp();
        }

        return mContext;
    }

    public static void shortToast(CharSequence text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    public static void longToast(CharSequence text) {
        toast(text, Toast.LENGTH_LONG);
    }

    public static void toast(CharSequence text, int duration) {
        Toast.makeText(getContext(), text, duration).show();
    }
}
