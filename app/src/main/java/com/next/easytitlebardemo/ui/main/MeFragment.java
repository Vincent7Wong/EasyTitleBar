package com.next.easytitlebardemo.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.next.easytitlebar.utils.EasyUtil;
import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;


/**
 * Created by Administrator on 2018/7/30.
 * 我的
 */

public class MeFragment extends Fragment {

   private NestedScrollView mSrollView;
    private EasyTitleBar titleBar;
    private boolean isBlack = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_me, null);
        titleBar = mContentView.findViewById(R.id.titleBar);
        mSrollView = mContentView.findViewById(R.id.mSrollView);

        initEventAndData();

        return mContentView;
    }



    private void initEventAndData() {

        initTitleBarView();

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
                   // EasyStatusBarUtil.StatusBarLightMode(getActivity(), R.color.white, R.color.status_bar_color); //设置白底黑字
                    isBlack = true;
                } else {
                    isBlack = false;
                   // EasyStatusBarUtil.StatusBarDarkMode(getActivity(), ((MainActivity) getActivity()).getMode());
                    titleBar.setTitle("");
                    titleBar.setTitleColor(ContextCompat.getColor(getContext(), R.color.white));
                }
            }
        });
    }


}
