package com.next.easytitlebardemo.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.util.EasyStatusBarUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/30.
 * 消息
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.titleBar)
    EasyTitleBar titleBar;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onViewCreated() {

        initTitleFit();

        initTitleBarView();

    }

    @Override
    protected void initEventAndData() {

    }


    private void initTitleFit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            titleBar.setHasStatusPadding(true);
            if (((MainActivity) getActivity()).getMode() == 0) {
                titleBar.setFitColor(ContextCompat.getColor(getContext(), R.color.status_bar_color));
            } else {
                titleBar.setFitColor(ContextCompat.getColor(getContext(), R.color.white));
            }
        }
    }

    private void initTitleBarView() {
        //Java代码添加稍复杂view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.schedule_menu_view, null);
        titleBar.addRightView(view);
        titleBar.getRightLayout(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleBar.getBackLayout().setVisibility(View.VISIBLE);
                if (titleBar.getTitleStyle() == 1) {
                    titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_CENTER);
                } else {
                    titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
                }
            }
        });


        //Java代码添加图片及监听事件
        titleBar.addRightImg(R.mipmap.icon_contact, new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
            @Override
            public void OnMenuEvent() {
                Toast.makeText(getContext(), "没有联系人", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void lazyLoad() {
        super.lazyLoad();
        EasyStatusBarUtil.StatusBarLightMode(getActivity(), R.color.white, R.color.status_bar_color); //设置白底黑字
    }

}
