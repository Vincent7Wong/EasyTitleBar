package com.next.easytitlebardemo.ui;

import android.view.View;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseActivity;

import butterknife.BindView;

/**
 * 历史浏览
 */
public class HistoryActivity extends BaseActivity {

    private EasyTitleBar titleBar;

    private boolean isSelect;

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
        titleBar = findViewById(R.id.title);
        titleBar.getBackLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelect){
                    titleBar.setBackImageRes(R.mipmap.back_icon);
                }else{
                    titleBar.setBackImageRes(R.mipmap.e_logo);
                }
                isSelect = !isSelect;
            }
        });

    }

    @Override
    protected void initEventAndData() {

    }
}
