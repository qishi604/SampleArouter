package com.sc.basiclib.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Activity 基类
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public abstract class BaseActivity extends AppCompatActivity implements IPage {

    protected View mLayoutView;

    @Override
    public View getLayoutView(LayoutInflater inflater) {
        if (mLayoutView == null) {
            final int layoutRes = getLayoutRes();
            if (layoutRes != 0) {
                mLayoutView = inflater.inflate(layoutRes, null);
            }
        }

        return mLayoutView;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View layoutView = getLayoutView(getLayoutInflater());
        mLayoutView = layoutView;

        if (layoutView != null) {
            setContentView(layoutView);
            render(savedInstanceState);
        }

        CharSequence title = getClass().getSimpleName().replace("Activity", "");
        setTitle(title);
    }
}
