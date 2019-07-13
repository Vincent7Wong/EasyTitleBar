package com.next.easytitlebardemo.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.bean.local.ActivityBean;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/6/1.
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        initCallBack();

        initEasyTitleBar();

    }

    private void initCallBack() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {

                ActivityBean bean = new ActivityBean();
                Unbinder unbinder = ButterKnife.bind(activity);
                bean.setUnbinder(unbinder);
                activity.getIntent().putExtra("ActivityBean", bean);

                //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
                if (activity.findViewById(R.id.titleBar) != null) { //找到 Toolbar 并且替换 Actionbar
                    ((EasyTitleBar) activity.findViewById(R.id.titleBar)).getBackLayout().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            activity.onBackPressed();
                        }
                    });
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityBean bean = activity.getIntent().getParcelableExtra("ActivityBean");
                bean.getUnbinder().unbind();
            }

        });
    }

    private void initEasyTitleBar() {
        EasyTitleBar.init()
                .backIconRes(R.mipmap.back_icon)
                .backgroud(ContextCompat.getColor(this, R.color.white))
                .titleSize(18)
                .showLine(true)
                .titleColor(ContextCompat.getColor(this, R.color.common_text_3))
                .titleBarHeight(52);
    }

}
