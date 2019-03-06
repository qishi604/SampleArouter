package com.android.sample.arouter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 *
 * Demo Application
 * @author seven
 * @version 1.0
 * @since 2019/3/6
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initARouter(this);
    }

    private void initARouter(Application app) {
        // 开启调试必须在 init 之前调用，否则会无效
        if (BuildConfig.DEBUG) {
            ARouter.openLog(); // 开启日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        ARouter.init(app);
    }
}
