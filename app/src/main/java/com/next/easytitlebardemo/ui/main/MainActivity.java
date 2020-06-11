package com.next.easytitlebardemo.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.next.easynavigation.view.EasyNavigationBar;
import com.next.easytitlebardemo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 入口
 */
public class MainActivity extends AppCompatActivity {

    private EasyNavigationBar mNavigitionBar;


    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigitionBar = findViewById(R.id.mNavigitionBar);

        initNavigition();
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
                .build();
    }

}
