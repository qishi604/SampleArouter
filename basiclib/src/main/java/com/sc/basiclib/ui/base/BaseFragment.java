package com.sc.basiclib.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment 基类
 * @author seven
 * @version 1.0
 * @since 2019/3/9
 */
public abstract class BaseFragment extends Fragment implements IPage {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutView = getLayoutView(inflater);
        return mLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        render(savedInstanceState);
    }
}
