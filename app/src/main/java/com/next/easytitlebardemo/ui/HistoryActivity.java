package com.next.easytitlebardemo.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;


/**
 * 历史浏览
 */
public class HistoryActivity extends AppCompatActivity {

    private EasyTitleBar titleBar;

    private boolean isSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        titleBar = findViewById(R.id.title);
        titleBar.getBackLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelect){
                    titleBar.setBackImageRes(R.mipmap.back_icon);
                }else{
                    titleBar.setBackImageRes(R.mipmap.e_logo);
                }
                isSelect = !isSelect;
            }
        });
    }

}
