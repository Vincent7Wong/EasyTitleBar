package com.next.easytitlebardemo.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;


/**
 * Created by Administrator on 2018/7/30.
 * 消息
 */

public class MessageFragment extends Fragment {

   private EasyTitleBar titleBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_message, null);
        titleBar = mContentView.findViewById(R.id.titleBar);

        initEventAndData();

        return mContentView;
    }

    private void initEventAndData() {


        initTitleBarView();

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

}
