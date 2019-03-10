package com.sc.sample.arouter;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sc.basiclib.IApp;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
@Route(path = "/service/app")
public class AppImpl implements IApp {

    private Application mApp;

    @Override
    public Application getApp() {
        return mApp;
    }

    @Override
    public void init(Context context) {
        if (context instanceof Application) {
            mApp = (Application) context;
        }
    }
}
