package com.sc.sample.arouter.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sc.basiclib.util.EventManager;
import com.sc.basiclib.util.MainLooper;
import com.sc.basiclib.util.ToastManager;
import com.sc.service.model.LoginStatus;
import com.sc.service.router.Constants;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

/**
 * 登录拦截器
 * ARouter通过 aop 方式自动注册，无需手动注册
 *
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
@Interceptor(priority = 8, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {

    /**
     * 因为涉及到多线程调用，所以这里用 volatile 变量
     */
    private volatile boolean mIsLogin;

    private WeakReference<Postcard> mPostcardRef;

    private WeakReference<InterceptorCallback> mCallbackRef;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        System.out.println("LoginInterceptor " + postcard.getPath());

        if (mIsLogin) {
            callback.onContinue(postcard);
            return;
        }

        final String path = postcard.getPath();
        // 过滤掉非用户相关的路由
        if (TextUtils.isEmpty(path) || path.equals(Constants.USER.LOGIN)) {
            callback.onContinue(postcard);
            return;
        }

        boolean needLogin = path.startsWith(Constants.USER.PREFIX);

        if (needLogin) {
            mPostcardRef = new WeakReference<>(postcard);
            mCallbackRef = new WeakReference<>(callback);

            showLogin();

        } else {
            callback.onContinue(postcard);
        }
    }

    /**
     * 拦截器被初始化时调用，只会调用一次
     * 即ARouter.init(Application) 的时候会调用
     * @param context 这个 context 就是 Application
     */
    @Override
    public void init(Context context) {
        // 注册event bus，接收登录、登出消息，但是没地方注销。。。
        // 如果拦截器的生命周期和Application一样的话，不注销问题也不大
        EventManager.register(this);
    }

    @Subscribe
    public void onEvent(LoginStatus event) {
        if (event != null) {
            mIsLogin = event.isLogin;

            System.out.println("接收到登录成功回调 准备跳转登录 mIsLogin " + mIsLogin + " postcard " + mPostcardRef.get() + " callback " + mCallbackRef.get());

            if (mIsLogin) {
                onLoginSuccess();
            }
        }
    }

    private void showLogin() {
        MainLooper.run(new Runnable() {
            @Override
            public void run() {
                ToastManager.getInstance().longToast("拦截到用户相关页面，未登录，请先登录");
            }
        });
        ARouter.getInstance().build(Constants.USER.LOGIN).navigation();
    }

    private void onLoginSuccess() {
        final Postcard postcard = mPostcardRef.get();
        final InterceptorCallback callback = mCallbackRef.get();
        if (null != postcard && null != callback) {
            callback.onContinue(postcard);

            mPostcardRef = null;
            mCallbackRef = null;
        }
    }
}
