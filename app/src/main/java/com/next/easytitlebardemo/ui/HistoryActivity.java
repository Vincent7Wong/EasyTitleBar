package com.next.easytitlebardemo.ui;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseActivity;

import butterknife.BindView;

/**
 * 历史浏览
 */
public class HistoryActivity extends BaseActivity {

    private EasyTitleBar titleBar;

    @Override
    protected int getScreenMode() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void onViewCreated() {
        titleBar = findViewById(R.id.titleBar);
        titleBar.setBackgroundResource(R.mipmap.e_logo);
    }

    @Override
    protected void initEventAndData() {

    }
}
