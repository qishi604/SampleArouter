package com.sc.basiclib.util;


import android.os.Handler;
import android.os.Looper;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
public class MainLooper {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void run(Runnable r) {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            r.run();
        } else {
            HANDLER.post(r);
        }
    }
}
