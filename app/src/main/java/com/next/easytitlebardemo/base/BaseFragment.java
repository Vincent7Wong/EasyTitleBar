package com.next.easytitlebardemo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;


/**
 * Created by Jue on 15/11/4.
 */
public abstract class BaseFragment extends Fragment {


    private View mContentView;

    private boolean isUIVisible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContentView = inflater.inflate(getLayoutId(), null);

        ButterKnife.bind(this, mContentView);

        onViewCreated();

        initEventAndData();

        if (isUIVisible) {
            loadData();
        }
        return mContentView;
    }

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUIVisible = isVisibleToUser;
        if (isUIVisible) {
            loadData();
        }
    }

    public View getmContentView() {
        return mContentView;
    }

    private void loadData() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (getView() != null && isUIVisible) {
            lazyLoad();
            //数据加载完毕,恢复标记,防止重复加载
            isUIVisible = false;
        }
    }

    public void lazyLoad() {
    }

}