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
public final class ToastManager {

    private Application mContext;

    private static class Holder {
        private static final ToastManager INSTANCE = new ToastManager();
    }

    public static ToastManager getInstance() {
        return Holder.INSTANCE;
    }

    private ToastManager() {
        ARouter.getInstance().inject(this);
    }

    private Application getContext() {
        if (null == mContext) {
            // 这个path 应该写到Constants 里面，这里没想好放哪~
            IApp iApp = (IApp) ARouter.getInstance().build("/service/toast").navigation();
            mContext = iApp.getApp();
        }

        return mContext;
    }

    public void shortToast(CharSequence text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    public void longToast(CharSequence text) {
        toast(text, Toast.LENGTH_LONG);
    }

    public void toast(CharSequence text, int duration) {
        Toast.makeText(getContext(), text, duration).show();
    }
}
