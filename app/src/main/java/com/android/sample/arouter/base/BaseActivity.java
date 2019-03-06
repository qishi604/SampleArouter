package com.android.sample.arouter.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author seven
 * @version 1.0
 * @since 2019/3/6
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onPostCreate(@androidx.annotation.Nullable Bundle savedInstanceState, @androidx.annotation.Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        setTitle(title());
    }

    protected String title() {
        return getClass().getSimpleName();
    }
}
