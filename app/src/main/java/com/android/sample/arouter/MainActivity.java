package com.android.sample.arouter;

import android.os.Bundle;

import com.android.sample.arouter.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected String title() {
        return "ARouter Sample"
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
