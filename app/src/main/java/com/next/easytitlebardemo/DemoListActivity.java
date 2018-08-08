package com.next.easytitlebardemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/6/29.
 */

public class DemoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_demo_list);

        super.onCreate(savedInstanceState);
    }


    public void onNormal(View view){
        startActivity(new Intent(DemoListActivity.this, com.next.easytitlebardemo.MainActivity.class));
    }

    public void onClick3(View view){
        startActivity(new Intent(DemoListActivity.this, com.next.easytitlebardemo.ui.demo.MainActivity.class));
    }
}
