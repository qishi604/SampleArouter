package com.sc.account.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sc.account.R;
import com.sc.account.data.UserProvider;
import com.sc.basiclib.ui.base.BaseFragment;
import com.sc.service.model.User;
import com.sc.service.router.Constants;

/**
 * 用户列表
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
@Route(path = Constants.USER.LIST_FRAGMENT)
public class UserListFragment extends BaseFragment {

    private boolean mIsSelect;

    @Override
    public int getLayoutRes() {
        return R.layout.user_fragment_list;
    }

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (null != args) {
            mIsSelect = args.getBoolean("select");
        }

        RecyclerView rv = mLayoutView.findViewById(R.id.recyclerView);
        BaseQuickAdapter<User, UserViewHolder> adapter = new BaseQuickAdapter<User, UserViewHolder>(R.layout.item_user) {
            @Override
            protected void convert(UserViewHolder holder, User item) {
                holder.bind(item);
            }
        };

        if (mIsSelect) {
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    User user = (User) adapter.getItem(position);
                    Intent intent = new Intent();
                    intent.putExtra("data", user);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            });
        }

        rv.setAdapter(adapter);
        adapter.replaceData(UserProvider.getUsers(100));
    }
}
