package com.sc.sample.arouter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.sample.arouter.R;
import com.sc.basiclib.ui.base.BaseActivity;
import com.sc.service.router.Constants;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
@Route(path = Constants.MAIN.SIMPLE)
public class SimpleActivity extends BaseActivity {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_simple;
    }

    @Override
    public void render(@Nullable Bundle savedInstanceState) {

    }
}
