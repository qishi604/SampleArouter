package com.sc.basiclib.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 页面接口，主要是为了统一Activity 和 Fragment View的设置。方便Activity和Fragment之间切换
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public interface IPage {

    /**
     * 返回页面布局
     * @return 页面布局layout
     */
    @LayoutRes int getLayoutRes();

    /**
     * 返回页面Layout View。如果不想通过xml布局，则最好重写这个方法返回View
     * @return View
     */
    View getLayoutView(LayoutInflater inflater);

    /**
     * 渲染页面（可以在这里做控件的处理）
     */
    void render(@Nullable Bundle savedInstanceState);

    Activity getActivity();
}
