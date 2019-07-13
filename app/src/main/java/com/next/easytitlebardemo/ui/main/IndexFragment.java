package com.next.easytitlebardemo.ui.main;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.util.EasyStatusBarUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/30.
 * 首页
 */

public class IndexFragment extends BaseFragment {

    @BindView(R.id.titleBar)
    EasyTitleBar titleBar;
    private TextView locationText;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void onViewCreated() {

        initTitleFit();

        initTitleBarView();

    }

    @Override
    protected void initEventAndData() {

    }

    private void initTitleBarView() {
        //添加并获取改view
        locationText = (TextView) new EasyTitleBar.MenuBuilder(getContext(), titleBar)
                .text("北京")
                .menuTextColor(ContextCompat.getColor(getContext(),R.color.appColor))
                .onItemClickListener(new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
                    @Override
                    public void OnMenuEvent() {
                        locationText.setText("上海");
                    }
                })
                .createText();
        titleBar.addLeftView(locationText);
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

    @Override
    public void lazyLoad() {
        super.lazyLoad();
        EasyStatusBarUtil.StatusBarLightMode(getActivity(), R.color.white, R.color.status_bar_color); //设置白底黑字
    }


}
