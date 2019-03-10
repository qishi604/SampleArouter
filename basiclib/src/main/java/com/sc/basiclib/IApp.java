package com.sc.basiclib;

import android.app.Application;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/10
 */
public interface IApp extends IProvider {

    Application getApp();
}
