package com.next.easytitlebar.view;

import android.content.Context;
import android.graphics.Color;

import com.next.easytitlebar.R;

/**
 * Created by Administrator on 2018/6/23.
 */

public class TitleBarSetting {


    private static TitleBarSetting setting;

    //默认返回图标
    private int back_icon = R.drawable.icon_back;

    //默认背景颜色
    private int backgroud = Color.parseColor("#62e3ec");

    //默认标题大小
    private int titleSize = 18;

    //默认标题颜色
    private int titleColor = Color.parseColor("#ffffff");

    //标题栏分割线的有无
    private boolean lineShow = true;

    //默认标题栏高度
    private int titleBarHeight = 48;
    private int parentPadding = 15;
    private int viewPadding = 20;
    //返回图片的大小
    private int backImageSize = 18;

    //menu图片大小
    private int menuImgSize = 18;
    //menu文字大小
    private int menuTextSize = 16;
    //menu文字颜色
    private int menuTextColor = Color.parseColor("#ffffff");
    private int titleStyle;

    private int lineHeight = 1;
    private int lineColor = Color.parseColor("#f7f7f7");
    private boolean fitSystemWindow = false;
    private boolean hasStatusPadding = false;

    private TitleBarSetting() {
    }

    public static TitleBarSetting getInstance() {
        if (setting == null) {
            setting = new TitleBarSetting();
        }
        return setting;
    }

    public TitleBarSetting lineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public TitleBarSetting backIconRes(int back_icon) {
        this.back_icon = back_icon;
        return this;
    }

    public TitleBarSetting menuTextSize(int menuTextSize) {
        this.menuTextSize = menuTextSize;
        return this;
    }

    public TitleBarSetting menuTextColor(int menuTextColor) {
        this.menuTextColor = menuTextColor;
        return this;
    }

    public TitleBarSetting backgroud(int backgroud) {
        this.backgroud = backgroud;
        return this;
    }

    public TitleBarSetting titleSize(int titleSize) {
        this.titleSize = titleSize;
        return this;
    }

    public TitleBarSetting titleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public TitleBarSetting ti(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public TitleBarSetting showLine(boolean lineShow) {
        this.lineShow = lineShow;
        return this;
    }

    public TitleBarSetting parentPadding(int parentPadding) {
        this.parentPadding = parentPadding;
        return this;
    }

    public TitleBarSetting titleBarHeight(int height) {
        this.titleBarHeight = height;
        return this;
    }

    public TitleBarSetting backImageSize(int size) {
        this.backImageSize = size;
        return this;
    }

    public TitleBarSetting menuImgSize(int size) {
        this.menuImgSize = size;
        return this;
    }

    public TitleBarSetting titleStyle(int titleStyle) {
        this.titleStyle = titleStyle;
        return this;
    }

    public TitleBarSetting lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }


    public TitleBarSetting fitSystemWindow(boolean fitSystemWindow) {
        this.fitSystemWindow = fitSystemWindow;
        return this;
    }

    public TitleBarSetting hasStatusPadding(boolean hasStatusPadding) {
        this.hasStatusPadding = hasStatusPadding;
        return this;
    }

    public int getTitleStyle() {
        return titleStyle;
    }

    public int getBackImageSize() {
        return backImageSize;
    }


    public int getMenuTextSize() {
        return menuTextSize;
    }

    public int getMenuTextColor() {
        return menuTextColor;
    }

    public int getBack_icon() {
        return back_icon;
    }

    public int getBackgroud() {
        return backgroud;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public boolean getShowLine() {
        return lineShow;
    }

    public int getTitleBarHeight() {
        return titleBarHeight;
    }

    public void setTitleBarHeight(int height) {
        this.titleBarHeight = height;
    }

    public int getParentPadding() {
        return parentPadding;
    }

    public int getViewPadding() {
        return viewPadding;
    }


    public int getMenuImgSize() {
        return menuImgSize;
    }


    public int getLineHeight() {
        return lineHeight;
    }


    public int getLineColor() {
        return lineColor;
    }


    public boolean isFitSystemWindow() {
        return fitSystemWindow;
    }

    public boolean isHasStatusPadding() {
        return hasStatusPadding;
    }
}
