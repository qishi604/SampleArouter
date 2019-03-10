package com.sc.basiclib.util;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sc.basiclib.IApp;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
public final class ToastManager {

    private Application mContext;

    // 使用依赖注入的方式发现服务，通过注解标注字段，inject 调用之后即可使用，无需手动获取
    @Autowired
    IApp mApp;

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
            mContext = mApp.getApp();
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
