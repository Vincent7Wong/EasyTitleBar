package com.next.easytitlebardemo.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.ui.HistoryActivity;

/**
 * Created by Administrator on 2018/7/30.
 * 发现
 */

public class DiscoverFragment extends Fragment {

    private EasyTitleBar titleBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View mContentView = inflater.inflate(R.layout.fragment_discover, null);

       titleBar = mContentView.findViewById(R.id.titleBar);

        initEventAndData();

       return mContentView;
    }


    private void initEventAndData() {

        titleBar.getRightLayout(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HistoryActivity.class));
            }
        });
    }



}
