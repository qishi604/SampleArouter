package com.sc.account.ui;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.sc.account.R;
import com.sc.service.model.User;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public class UserViewHolder extends BaseViewHolder {

    public UserViewHolder(View view) {
        super(view);
    }

    public void bind(User d) {
        setAssociatedObject(d);
        setText(R.id.tv_name, d.name);
    }
}
