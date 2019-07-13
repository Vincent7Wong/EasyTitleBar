package com.next.easytitlebardemo.ui.main;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.ui.HistoryActivity;
import com.next.easytitlebardemo.util.EasyStatusBarUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/30.
 * 发现
 */

public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.titleBar)
    EasyTitleBar titleBar;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void onViewCreated() {

        initTitleFit();

    }

    @Override
    protected void initEventAndData() {
        titleBar.getRightLayout(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HistoryActivity.class));
            }
        });
    }

    private void initTitleFit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            titleBar.setHasStatusPadding(true);
            if(((MainActivity)getActivity()).getMode()==0){
                titleBar.setFitColor(ContextCompat.getColor(getContext(), R.color.status_bar_color));
            }else{
                titleBar.setFitColor(ContextCompat.getColor(getContext(),R.color.white));
            }
        }
    }

    @Override
    public void lazyLoad() {
        super.lazyLoad();
        EasyStatusBarUtil.StatusBarLightMode(getActivity(), R.color.white, R.color.status_bar_color); //设置白底黑字
    }

}
