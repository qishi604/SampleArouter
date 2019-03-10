package com.sc.account.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sc.account.R;
import com.sc.basiclib.ui.base.BaseActivity;
import com.sc.service.router.Constants;

@Route(path = Constants.USER.LIST)
public class UserListActivity extends BaseActivity {

    @Autowired
    boolean select;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_container;
    }

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        // 别忘了调用注入
        ARouter.getInstance().inject(this);

        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putBoolean("select", select);
        fragment.setArguments(args);

        addFragment(fragment);
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName()).commit();
    }
}
