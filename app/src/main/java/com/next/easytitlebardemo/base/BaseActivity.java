package com.next.easytitlebardemo.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.util.EasyStatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by Jue on 2018/6/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private boolean isBlack = true;
    private int mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (getScreenMode() == 1) {
            //魅族MX3(4.2.1)出错
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        } else if (getScreenMode() == 2) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(getLayoutId());

        onViewCreated();

        initEventAndData();

        setTextBlack();

        super.onCreate(savedInstanceState);
    }

    /**
     * 屏幕状态
     */
    protected abstract int getScreenMode();

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 在initEventAndData()之前执行
     */
    protected abstract void onViewCreated();

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initView();
    }

    public void setTextBlack() {
        if (isBlack) {
            mode =  EasyStatusBarUtil.StatusBarLightMode(this, R.color.white, R.color.status_bar_color); //设置白底黑字
        }
    }


    public void setBlack(boolean black) {
        isBlack = black;
    }


    public int getMode() {
        return mode;
    }
}
