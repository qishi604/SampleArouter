package com.sc.sample.arouter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sc.basiclib.ui.base.BaseActivity;
import com.sc.basiclib.util.ToastUtils;
import com.sc.basiclib.util.ViewUtils;
import com.sc.service.model.User;
import com.sc.service.router.Constants;

import java.util.List;


public class MainActivity extends BaseActivity {

    private static final int REQUEST_PICK_USER = 11;

    private static final String[] TITELS = {
            "简单的应用内跳转",
            "带参数并接收返回数据",
            "获取fragment实例",
            "通过url跳转",
            "拦截器的使用",
            "依赖注入"
    };

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public View getLayoutView(LayoutInflater inflater) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        LinearLayout parent = (LinearLayout) mLayoutView;
        final int N = TITELS.length;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = ViewUtils.dp(12);
        for (int i = 0; i < N; i++) {
            Button button = new Button(this);
            button.setText(TITELS[i]);
            button.setTag(i);
            button.setOnClickListener(mItemClickListener);
            parent.addView(button, lp);
        }
    }

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int index = (int) v.getTag();

            switch (index) {
                case 0:
                    simpleNav();
                    break;
                case 1:
                    pickAUser();
                    break;
                case 2:
                    getUserListFragment();
                    break;
                case 3:
                    showWeb();
                    break;
                case 4:
                    showDialog("拦截器的使用", "拦截器的使用请参考readme，或者直接看源码 com.sc.sample.arouter.interceptor.LoginInterceptor");
                    break;
                case 5:
                    showDialog("依赖注入", "请查看工程的 ToastManager 或 ToastUtils 源码");
                    ToastUtils.shortToast("Toast 注入了 Context");
                    break;
            }
        }
    };

    private void demo() {
        ARouter.getInstance() // 获取ARouter 单例
                .build("/user/detail") // 目标 Activity path
                .withString("id", "10001") // withXxx 方法设置参数，数据类型与Android 的 Bundle 一一对应
//                .navigation() // 简单的跳转
                .navigation(this, 11) // 设置Activity 和 request_code 类似于 startActivityForResult()，这样就可以在 onActivityResult 中接收数据
        ;
    }

    private void simpleNav() {
        // ARouter 的简单用法
        ARouter.getInstance().build(Constants.MAIN.SIMPLE)
                .navigation();
    }

    private void pickAUser() {
        // 跳转到 Account 组件的用户列表页面
        // 添加参数通过 withXxx 方法
        // navigation 有多个重载方法
        ARouter.getInstance().build(Constants.USER.LIST)
                .withBoolean("select", true)// 传递参数
                .navigation(this, REQUEST_PICK_USER); // 类似于调用 startActivityForResult
    }

    private void showUserDetail(User user) {
        // 传递序列化参数
        // 非序列化的参数需要实现序列化接口
        ARouter.getInstance().build(Constants.USER.DETAIL)
                .withSerializable("user", user)
                .navigation();
    }

    private void getUserListFragment() {
        // 这里只是简单的应用内跳转，获取fragment逻辑在 FragmentContainerActivity
        startActivity(new Intent(this, FragmentContainerActivity.class));
    }

    private void showWeb() {
        ARouter.getInstance().build(Constants.WEB.COMMON)
                .withString("url", "file:///android_asset/schame-test.html")
                .navigation();
    }

    private void showDialog(String title, String content) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PICK_USER) {
            // 这里获取到数据，直接跳转到用户详情
            User user = (User) data.getSerializableExtra("data");
            showUserDetail(user);
        }
    }
}
