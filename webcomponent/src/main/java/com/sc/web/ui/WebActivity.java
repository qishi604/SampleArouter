package com.sc.web.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sc.basiclib.ui.base.BaseActivity;
import com.sc.service.router.Constants;
import com.sc.web.R;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
@Route(path = Constants.WEB.COMMON)
public class WebActivity extends BaseActivity {

    private WebView mWebView;

    @Autowired
    String url;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_web;
    }

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        mWebView = findViewById(R.id.webView);

        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
    }
}
