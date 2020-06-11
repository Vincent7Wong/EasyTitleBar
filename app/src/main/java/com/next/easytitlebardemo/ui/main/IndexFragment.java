package com.next.easytitlebardemo.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;


/**
 * Created by Administrator on 2018/7/30.
 * 首页
 */

public class IndexFragment extends Fragment {

    private EasyTitleBar titleBar;
    private TextView locationText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_index, null);

        titleBar = mContentView.findViewById(R.id.titleBar);

        initTitleBarView();

        return mContentView;
    }


    private void initTitleBarView() {
        //添加并获取改view
        locationText = (TextView) new EasyTitleBar.MenuBuilder(getContext(), titleBar)
                .text("北京")
                .menuTextColor(ContextCompat.getColor(getContext(), R.color.appColor))
                .onItemClickListener(new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
                    @Override
                    public void OnMenuEvent() {
                        locationText.setText("上海");
                    }
                })
                .createText();
        titleBar.addLeftView(locationText);
    }



}
