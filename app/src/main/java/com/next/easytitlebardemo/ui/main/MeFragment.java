package com.next.easytitlebardemo.ui.main;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.next.easytitlebar.utils.EasyUtil;
import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.util.EasyStatusBarUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/30.
 * 我的
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.mSrollView)
    NestedScrollView mSrollView;
    @BindView(R.id.titleBar)
    EasyTitleBar titleBar;
    private boolean isBlack = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onViewCreated() {

        initTitleFit();

        initTitleBarView();

    }

    @Override
    protected void initEventAndData() {
        titleBar.setOnDoubleClickListener(new EasyTitleBar.OnDoubleClickListener() {
            @Override
            public void onDoubleEvent(View view) {
                mSrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mSrollView.fling(0);
                       // mSrollView.smoothScrollTo(0, 0);
                        mSrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });

            }
        });
    }


    private void initTitleBarView() {
        titleBar.attachScrollView(mSrollView, R.color.white, EasyUtil.dip2px(getContext(), 250) + titleBar.getHeight(), new EasyTitleBar.OnSrollAlphaListener() {
            @Override
            public void OnSrollAlphaEvent(float alpha) {
                if (alpha > 0.8) {
                    titleBar.setTitle("我的");
                    titleBar.setTitleColor(ContextCompat.getColor(getContext(), R.color.common_text_3));
                    EasyStatusBarUtil.StatusBarLightMode(getActivity(), R.color.white, R.color.status_bar_color); //设置白底黑字
                    isBlack = true;
                } else {
                    isBlack = false;
                    EasyStatusBarUtil.StatusBarDarkMode(getActivity(), ((MainActivity) getActivity()).getMode());
                    titleBar.setTitle("");
                    titleBar.setTitleColor(ContextCompat.getColor(getContext(), R.color.white));
                }
            }
        });
    }

    private void initTitleFit() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            titleBar.setHasStatusPadding(true);
            if (((MainActivity) getActivity()).getMode() == 0) {
                titleBar.setFitColor(ContextCompat.getColor(getContext(), R.color.status_bar_color));
            } else {
                titleBar.setFitColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            }
        }

    }

    @Override
    public void lazyLoad() {
        super.lazyLoad();
        if (isBlack) {
            EasyStatusBarUtil.StatusBarLightMode(getActivity(), R.color.white, R.color.status_bar_color); //设置白底黑字
        } else {
            EasyStatusBarUtil.StatusBarDarkMode(getActivity(), ((MainActivity) getActivity()).getMode());
        }
    }

}
