package com.sc.sample.arouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.sample.arouter.R;
import com.sc.basiclib.ui.base.BaseActivity;
import com.sc.service.router.Constants;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public class FragmentContainerActivity extends BaseActivity {

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_container;
    }

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        // 通过ARouter获取 Fragment 实例
        Fragment fragment = (Fragment) ARouter.getInstance().build(Constants.USER.LIST_FRAGMENT).navigation();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "list").commit();
    }
}
