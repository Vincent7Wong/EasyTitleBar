package com.next.easytitlebardemo.ui.main;

import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.view.EasyNavigationBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseActivity;
import com.next.easytitlebardemo.util.EasyStatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 入口
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.mNavigitionBar)
    EasyNavigationBar mNavigitionBar;


    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();

    @Override
    protected int getScreenMode() {
        return 1;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated() {


        EasyStatusBarUtil.StatusBarLightMode(this, R.color.white, R.color.status_bar_color); //设置白底黑字

        initNavigition();
    }


    @Override
    protected void initEventAndData() {

    }

    private void initNavigition() {
        fragments.add(new IndexFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MeFragment());

        mNavigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .anim(Anim.ZoomIn)
                .build();
    }

}
