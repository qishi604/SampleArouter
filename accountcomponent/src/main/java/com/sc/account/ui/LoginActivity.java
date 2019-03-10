package com.sc.account.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sc.account.R;
import com.sc.basiclib.ui.base.BaseActivity;
import com.sc.basiclib.util.EventManager;
import com.sc.service.model.LoginStatus;
import com.sc.service.router.Constants;

/**
 * 登录页面
 */
@Route(path = Constants.USER.LOGIN)
public class LoginActivity extends BaseActivity {

    @Override
    public int getLayoutRes() {
        return R.layout.user_activity_login;
    }

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        LoginStatus event = new LoginStatus(true);
        EventManager.post(event);
        finish();
    }
}

