package com.sc.account.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sc.account.R;
import com.sc.account.data.UserProvider;
import com.sc.basiclib.ui.base.BaseActivity;
import com.sc.service.model.User;
import com.sc.service.router.Constants;

/**
 * 用户详情页
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
@Route(path = Constants.USER.DETAIL)
public class UserDetailActivity extends BaseActivity {

    @Autowired(name = "user")
    User mUser;

    @Autowired(name = "id")
    String mUserId;

    @Override
    public int getLayoutRes() {
        return R.layout.user_activity_user_detail;
    }

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        // 调用这个方法，@Autowired 标注的属性才能被注入值
        ARouter.getInstance().inject(this);

        if (null == mUser) {
            if (null != mUserId) {
                mUser = UserProvider.getUser(mUserId);
            }
        }

        if (mUser != null) {
            TextView tvName = mLayoutView.findViewById(R.id.tv_name);
            tvName.setText(mUser.name);
        }
    }
}
