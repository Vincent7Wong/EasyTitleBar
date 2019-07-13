package com.next.easytitlebar.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.next.easytitlebar.R;
import com.next.easytitlebar.utils.EasyUtil;

import java.util.logging.Logger;


/**
 * 通用标题栏
 */
public class EasyTitleBar extends RelativeLayout {

    private static TitleBarSetting titleBarSetting;

    private TextView title_tv;
    private LinearLayout titleLayout;
    private ConstraintLayout fit_cl;
    //返回箭头图片
    private ImageView backImage;
    //返回箭头的父布局
    private LinearLayout backLayout;
    private ViewGroup rightLayout;
    private ViewGroup leftLayout;
    private ViewGroup title_vg;
    //分割线
    private View titleLine;


    //menu图片大小
    private float menuImgSize;
    //menu文字大小
    private float menuTextSize;
    //menu文字颜色
    private int menuTextColor;

    //标题栏高度
    private float titleBarHeight;
    //标题栏背景
    private int titleBarBackGround;
    //填充状态栏的颜色
    private int fitColor;

    //左边的图标（一般为返回箭头）
    private int backRes;

    //返回箭头、左右viewgroup距两边的距离
    private float parentPadding;
    //左右viewgroup之间的距离
    private float viewPadding;

    //标题字体大小
    private float titleTextSize;
    //标题字体颜色
    private int titleColor;
    //标题字排列风格  居中或是居左
    private int titleStyle;

    public static final int TITLE_STYLE_LEFT = 1;
    public static final int TITLE_STYLE_CENTER = 0;

    //分割线高度
    private float lineHeight;
    //分割线颜色
    private int lineColor;
    private int backImageSize;

    private OnDoubleClickListener onDoubleClickListener;

    private ConstraintSet leftConstraintSet = new ConstraintSet();
    private ConstraintSet centerConstraintSet = new ConstraintSet();
    private int lineState;
    private GestureDetector detector;
    private String title;
    private int backLayoutState = 1;
    private boolean fitSystemWindow = false;
    private View status_view;

    private boolean hasStatusPadding;

    public EasyTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public EasyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs, 0);
    }

    public EasyTitleBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.easy_titlebar, this);

        fit_cl = findViewById(R.id.fit_cl);

        status_view = findViewById(R.id.status_view);

        backImage = findViewById(R.id.left_image);

        rightLayout = findViewById(R.id.right_layout);
        leftLayout = findViewById(R.id.left_layout);

        backLayout = findViewById(R.id.back_layout);

        title_tv = findViewById(R.id.title_tv);
        titleLayout = findViewById(R.id.root);
        titleLine = findViewById(R.id.line);


        leftConstraintSet.clone(fit_cl);
        centerConstraintSet.clone(fit_cl);

        initEvent();

        initSetting();

        parseStyle(context, attrs);
    }

    private void initEvent() {
        detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (onDoubleClickListener != null)
                    onDoubleClickListener.onDoubleEvent(title_tv);
                return super.onDoubleTap(e);
            }
        });

        titleLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
    }


    //初始化
    private void initSetting() {
        if (titleBarSetting == null)
            return;
        titleBarBackGround = titleBarSetting.getBackgroud();

        backRes = titleBarSetting.getBack_icon();
        titleTextSize = EasyUtil.sp2px(getContext(), titleBarSetting.getTitleSize());
        titleColor = titleBarSetting.getTitleColor();
        titleBarHeight = EasyUtil.dip2px(getContext(), titleBarSetting.getTitleBarHeight());

        parentPadding = EasyUtil.dip2px(getContext(), titleBarSetting.getParentPadding());
        viewPadding = EasyUtil.dip2px(getContext(), titleBarSetting.getViewPadding());

        backImageSize = EasyUtil.dip2px(getContext(), titleBarSetting.getBackImageSize());
        menuImgSize = EasyUtil.dip2px(getContext(), titleBarSetting.getMenuImgSize());
        menuTextColor = titleBarSetting.getMenuTextColor();
        menuTextSize = EasyUtil.sp2px(getContext(), titleBarSetting.getMenuTextSize());
        titleStyle = titleBarSetting.getTitleStyle();
        lineHeight = titleBarSetting.getLineHeight();
        lineColor = titleBarSetting.getLineColor();
        fitSystemWindow = titleBarSetting.isFitSystemWindow();
        hasStatusPadding = titleBarSetting.isHasStatusPadding();
        if (titleBarSetting.getShowLine()) {
            lineState = 1;
        } else {
            lineState = 0;
        }
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTitleBar);

            fitSystemWindow = ta.getBoolean(R.styleable.EasyTitleBar_Easy_fitsSystemWindows, fitSystemWindow);
            fit_cl.setFitsSystemWindows(fitSystemWindow);

            hasStatusPadding = ta.getBoolean(R.styleable.EasyTitleBar_Easy_hasStatusPadding, hasStatusPadding);
            if (hasStatusPadding) {
                LinearLayout.LayoutParams statusParams = (LinearLayout.LayoutParams) status_view.getLayoutParams();
                statusParams.height = EasyUtil.getStateBarHeight(getContext());
                status_view.setLayoutParams(statusParams);
            } else {
                LinearLayout.LayoutParams statusParams = (LinearLayout.LayoutParams) status_view.getLayoutParams();
                statusParams.height = 0;
                status_view.setLayoutParams(statusParams);
            }


            //返回箭头
            Drawable backDrawable = ta.getDrawable(R.styleable.EasyTitleBar_Easy_backRes);
            if (backDrawable != null) {
                backImage.setImageDrawable(backDrawable);
            } else {
                backImage.setImageResource(backRes);
            }


            //标题栏
            titleBarHeight = ta.getDimension(R.styleable.EasyTitleBar_Easy_titleBarHeight, titleBarHeight);
            titleBarBackGround = ta.getColor(R.styleable.EasyTitleBar_Easy_titleBarBackground, titleBarBackGround);
            titleLayout.setBackgroundColor(titleBarBackGround);
            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) fit_cl.getLayoutParams();
            titleParams.height = (int) titleBarHeight;
            fit_cl.setLayoutParams(titleParams);


            fitColor = titleBarBackGround;
            fitColor = ta.getColor(R.styleable.EasyTitleBar_Easy_fitColor, fitColor);
            status_view.setBackgroundColor(fitColor);

            //标题
            title = ta.getString(R.styleable.EasyTitleBar_Easy_title);
            if (null != title) {
                title_tv.setText(title);
            } else {
                if (context instanceof Activity) {
                    PackageManager pm = context.getPackageManager();
                    try {
                        ActivityInfo activityInfo = pm.getActivityInfo((((Activity) context).getComponentName()), 0);
                        setTitle(activityInfo.loadLabel(pm).toString());
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            titleTextSize = ta.getDimension(R.styleable.EasyTitleBar_Easy_titleSize, titleTextSize);
            title_tv.setTextSize(EasyUtil.px2sp(context, titleTextSize));
            titleColor = ta.getColor(R.styleable.EasyTitleBar_Easy_titleColor, titleColor);

            title_tv.setTextColor(titleColor);

            lineHeight = ta.getDimension(R.styleable.EasyTitleBar_Easy_lineHeight, lineHeight);
            lineColor = ta.getColor(R.styleable.EasyTitleBar_Easy_lineColor, lineColor);
            ConstraintLayout.LayoutParams lineParams = (ConstraintLayout.LayoutParams) titleLine.getLayoutParams();
            lineParams.height = (int) lineHeight;
            titleLine.setBackgroundColor(lineColor);
            titleLine.setLayoutParams(lineParams);


            //菜单图标大小
            menuImgSize = ta.getDimension(R.styleable.EasyTitleBar_Easy_menuImgSize, menuImgSize);
            //菜单文字大小
            menuTextSize = ta.getDimension(R.styleable.EasyTitleBar_Easy_menuTextSize, menuTextSize);
            //菜单文字颜色
            menuTextColor = ta.getColor(R.styleable.EasyTitleBar_Easy_menuTextColor, menuTextColor);

            //左边xml添加View
            int leftImageState = ta.getInt(R.styleable.EasyTitleBar_Easy_leftLayoutState, 1);
            if (leftImageState == 1) {
                leftLayout.setVisibility(VISIBLE);
            } else {
                leftLayout.setVisibility(GONE);
            }

            //One
            String leftOneText = ta.getString(R.styleable.EasyTitleBar_Easy_leftOneText);
            if (!TextUtils.isEmpty(leftOneText)) {
                addLeftView(new MenuBuilder(getContext(), this)
                        .text(leftOneText)
                        .createText());
            }
            Drawable leftOneImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_leftOneImage);
            if (leftOneImage != null) {
                addLeftView(new MenuBuilder(getContext(), this)
                        .drawable(leftOneImage)
                        .createImage());
            }
            //Two
            String leftTwoText = ta.getString(R.styleable.EasyTitleBar_Easy_leftTwoText);
            if (!TextUtils.isEmpty(leftTwoText)) {
                addLeftView(new MenuBuilder(getContext(), this)
                        .text(leftTwoText)
                        .createText());
            }
            Drawable leftTwoImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_leftTwoImage);
            if (leftTwoImage != null) {
                addLeftView(new MenuBuilder(getContext(), this)
                        .drawable(leftTwoImage)
                        .createImage());
            }
            //Three
            String leftThreeText = ta.getString(R.styleable.EasyTitleBar_Easy_leftThreeText);
            if (!TextUtils.isEmpty(leftThreeText)) {
                addLeftView(new MenuBuilder(getContext(), this)
                        .text(leftThreeText)
                        .createText());
            }
            Drawable leftThreeImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_leftThreeImage);
            if (leftThreeImage != null) {
                addLeftView(new MenuBuilder(getContext(), this)
                        .drawable(leftThreeImage)
                        .createImage());
            }

            //右侧xml添加View
            int rightImageState = ta.getInt(R.styleable.EasyTitleBar_Easy_rightLayoutState, 1);
            if (rightImageState == 1) {
                rightLayout.setVisibility(VISIBLE);
            } else {
                rightLayout.setVisibility(GONE);
            }


            //One
            String rightOneText = ta.getString(R.styleable.EasyTitleBar_Easy_rightOneText);
            if (!TextUtils.isEmpty(rightOneText)) {
                addRightView(new MenuBuilder(getContext(), this)
                        .text(rightOneText)
                        .createText());
            }
            Drawable rightOneImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_rightOneImage);
            if (rightOneImage != null) {
                addRightView(new MenuBuilder(getContext(), this)
                        .drawable(rightOneImage)
                        .createImage());
            }
            //Two
            String rightTwoText = ta.getString(R.styleable.EasyTitleBar_Easy_rightTwoText);
            if (!TextUtils.isEmpty(rightTwoText)) {
                addRightView(new MenuBuilder(getContext(), this)
                        .text(rightTwoText)
                        .createText());
            }
            Drawable rightTwoImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_rightTwoImage);
            if (rightTwoImage != null) {
                addRightView(new MenuBuilder(getContext(), this)
                        .drawable(rightTwoImage)
                        .createImage());
            }
            //Three
            String rightThreeText = ta.getString(R.styleable.EasyTitleBar_Easy_rightThreeText);
            if (!TextUtils.isEmpty(rightThreeText)) {
                addRightView(new MenuBuilder(getContext(), this)
                        .text(rightThreeText)
                        .createText());
            }
            Drawable rightThreeImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_rightThreeImage);
            if (rightThreeImage != null) {
                addRightView(new MenuBuilder(getContext(), this)
                        .drawable(rightThreeImage)
                        .createImage());
            }

            //放在titleStyle之前
            viewPadding = ta.getDimension(R.styleable.EasyTitleBar_Easy_viewPadding, viewPadding);
            parentPadding = ta.getDimension(R.styleable.EasyTitleBar_Easy_parentPadding, parentPadding);
            ConstraintLayout.LayoutParams backLayoutParams = (ConstraintLayout.LayoutParams) backLayout.getLayoutParams();
            backLayoutParams.width = (int) (backImageSize + parentPadding * 2);
            backLayout.setLayoutParams(backLayoutParams);

            leftLayout.setPadding((int) (parentPadding - viewPadding / 2), 0, 0, 0);

            rightLayout.setPadding(0, 0, (int) (parentPadding - (viewPadding / 2)), 0);


            backLayoutState = ta.getInt(R.styleable.EasyTitleBar_Easy_backLayoutState, 1);

            //分割线
            lineState = ta.getInt(R.styleable.EasyTitleBar_Easy_lineState, lineState);

            if (backLayoutState == 1) {
                backImage.setVisibility(VISIBLE);
                backLayout.setVisibility(VISIBLE);
            } else {
                backImage.setVisibility(GONE);
                backLayout.setVisibility(GONE);
            }

            if (lineState == 1) {
                titleLine.setVisibility(VISIBLE);
            } else {
                titleLine.setVisibility(GONE);
            }

//3
            titleStyle = ta.getInt(R.styleable.EasyTitleBar_Easy_titleStyle, titleStyle);
            if (titleStyle == 0) {
                setTitleStyle(TITLE_STYLE_CENTER);
            } else {
                setTitleStyle(TITLE_STYLE_LEFT);
            }

            ta.recycle();
        }
    }


    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }

    public void setEasyFitsWindows(boolean fitSystemWindow) {
        this.fitSystemWindow = fitSystemWindow;
        fit_cl.setFitsSystemWindows(fitSystemWindow);
    }

    public void setHasStatusPadding(boolean hasStatusPadding) {
        this.hasStatusPadding = hasStatusPadding;
        if (hasStatusPadding) {
            LinearLayout.LayoutParams statusParams = (LinearLayout.LayoutParams) status_view.getLayoutParams();
            statusParams.height = EasyUtil.getStateBarHeight(getContext());
            status_view.setLayoutParams(statusParams);
        } else {
            LinearLayout.LayoutParams statusParams = (LinearLayout.LayoutParams) status_view.getLayoutParams();
            statusParams.height = 0;
            status_view.setLayoutParams(statusParams);
        }
    }


    public void setFitColor(int fitColor) {
        this.fitColor = fitColor;
        status_view.setBackgroundColor(fitColor);
    }

    public void setBackgroundColor(int color) {
        titleLayout.setBackgroundColor(color);
    }

    public ViewGroup getLeftLayout() {
        return leftLayout;
    }

    public ViewGroup getRightLayout() {
        return rightLayout;
    }

    public float getMenuImgSize() {
        return menuImgSize;
    }

    public float getMenuTextSize() {
        return menuTextSize;
    }

    public int getMenuTextColor() {
        return menuTextColor;
    }

    /**
     * 返回箭头的父布局
     *
     * @return
     */
    public LinearLayout getBackLayout() {
        return backLayout;
    }

    public int getTitleStyle() {
        return titleStyle;
    }

    /**
     * 获取标题
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * 获取标题View
     *
     * @return
     */
    public TextView getTitleView() {
        return title_tv;
    }

    /**
     * 设置标题文字
     */
    public void setTitle(String title) {
        title_tv.setText(title);
    }

    /**
     * 设置标题字体大小
     */
    public void setTitleSize(float textSize) {
        title_tv.setTextSize(textSize);
    }

    /**
     * 设置标题字体颜色
     */
    public void setTitleColor(int textColor) {
        title_tv.setTextColor(textColor);
    }

    /**
     * 设置标题排列方式（一种居中、一种靠左）
     *
     * @param style
     */
    @SuppressLint("NewApi")
    public void setTitleStyle(int style) {
        titleStyle = style;

        if (backLayout.getVisibility() == VISIBLE && backImage.getVisibility() == VISIBLE) {
            backLayoutState = 1;
        } else {
            backLayoutState = 0;
        }
        if (titleLine.getVisibility() == VISIBLE) {
            lineState = 1;
        } else {
            lineState = 0;
        }

        if (style == TITLE_STYLE_CENTER) {
            centerConstraintSet.connect(title_tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
            centerConstraintSet.connect(title_tv.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
            centerConstraintSet.connect(title_tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            centerConstraintSet.connect(title_tv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
            centerConstraintSet.applyTo(fit_cl);
        } else if (style == TITLE_STYLE_LEFT) {
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.LEFT, backLayout.getId(), ConstraintSet.RIGHT, 0);
            leftConstraintSet.setMargin(title_tv.getId(), ConstraintSet.LEFT, 0);
            leftConstraintSet.setGoneMargin(title_tv.getId(), ConstraintSet.LEFT, (int) parentPadding);
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
            leftConstraintSet.applyTo(fit_cl);
        }

        ConstraintLayout.LayoutParams backLayoutParams = (ConstraintLayout.LayoutParams) backLayout.getLayoutParams();
        backLayoutParams.width = (int) (backImageSize + parentPadding * 2);
        backLayout.setLayoutParams(backLayoutParams);

        if (backLayoutState == 1) {
            backImage.setVisibility(VISIBLE);
            backLayout.setVisibility(VISIBLE);
        } else {
            backImage.setVisibility(GONE);
            backLayout.setVisibility(GONE);
        }

        if (lineState == 1) {
            titleLine.setVisibility(VISIBLE);
        } else {
            titleLine.setVisibility(GONE);
        }
    }


    /**
     * 获取标题分割线
     *
     * @return
     */
    public View getTitleLine() {
        return titleLine;
    }

    public void attachScrollView(View view, final int color, final int height, final OnSrollAlphaListener onSrollAlphaListener) {
        EasyUtil.addOnSrollListener(view, new EasyUtil.OnSrollListener() {
            @Override
            public void onSrollEvent(int scrollY) {
                int baseColor = getResources().getColor(color);
                float alpha = Math.min(1, (float) scrollY / height);
                setBackgroundColor(EasyUtil.getColorWithAlpha(alpha, baseColor));
                if (onSrollAlphaListener != null)
                    onSrollAlphaListener.OnSrollAlphaEvent(alpha);
            }
        });
    }

    //双击事件
    public interface OnDoubleClickListener {
        public void onDoubleEvent(View view);
    }

    public interface OnSrollAlphaListener {
        void OnSrollAlphaEvent(float alpha);
    }


    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static TitleBarSetting init() {
        titleBarSetting = TitleBarSetting.getInstance();
        return titleBarSetting;
    }

    public View getRightLayout(int position) {
        return rightLayout.getChildAt(rightLayout.getChildCount() - 1 - position);
    }

    public View getLeftLayout(int position) {
        return leftLayout.getChildAt(position);
    }


    public static class MenuBuilder {

        private Context context;

        private OnMenuClickListener onMenuClickListener;

        private String text;

        private int icon;
        private Drawable drawable;

        private int paddingleft;
        private int paddingright;

        private float menuImgSize;
        private float menuTextSize;
        private int menuTextColor;

        public MenuBuilder(Context context, EasyTitleBar titleBar) {
            this.context = context;
            paddingleft = (int) (titleBar.viewPadding / 2);
            paddingright = (int) (titleBar.viewPadding / 2);
            menuImgSize = titleBar.menuImgSize;
            menuTextSize = EasyUtil.px2sp(context, titleBar.menuTextSize);
            menuTextColor = titleBar.menuTextColor;
        }


        public MenuBuilder text(String text) {
            this.text = text;
            return this;
        }

        public MenuBuilder menuTextSize(float menuTextSize) {
            this.menuTextSize = menuTextSize;
            return this;
        }

        public MenuBuilder listener(OnMenuClickListener onMenuClickListener) {
            this.onMenuClickListener = onMenuClickListener;
            return this;
        }


        public MenuBuilder paddingleft(int paddingleft) {
            this.paddingleft = paddingleft;
            return this;
        }

        public MenuBuilder paddingright(int paddingright) {
            this.paddingright = paddingright;
            return this;
        }


        public MenuBuilder icon(int icon) {
            this.icon = icon;
            return this;
        }

        public MenuBuilder menuImgSize(int menuImgSize) {
            this.menuImgSize = menuImgSize;
            return this;
        }

        public MenuBuilder drawable(Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        public MenuBuilder menuTextColor(int menuTextColor) {
            this.menuTextColor = menuTextColor;
            return this;
        }

        public MenuBuilder menuTextSize(int menuTextSize) {
            this.menuTextSize = menuTextSize;
            return this;
        }

        public MenuBuilder onItemClickListener(MenuBuilder.OnMenuClickListener onMenuClickListener) {
            this.onMenuClickListener = onMenuClickListener;
            return this;
        }

        public View createText() {

            TextView textView = new TextView(context);
            textView.setText(text);
            textView.setTextSize(menuTextSize);
            textView.setTextColor(menuTextColor);
            textView.setPadding(paddingleft, 0, paddingright, 0);
            textView.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(textParams);

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null)
                        onMenuClickListener.OnMenuEvent();
                }
            });
            return textView;
        }

        public View createImage() {
            ImageView imageView = new ImageView(context);
            if (drawable != null)
                imageView.setImageDrawable(drawable);
            else if (icon != 0) {
                imageView.setImageResource(icon);
            } else {
                imageView.setImageBitmap(null);
            }
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageParams.width = (int) (menuImgSize + paddingleft + paddingright);
            imageView.setLayoutParams(imageParams);
            imageView.setPadding(paddingleft, 0, paddingright, 0);

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null)
                        onMenuClickListener.OnMenuEvent();
                }
            });

            return imageView;
        }


        public interface OnMenuClickListener {
            void OnMenuEvent();
        }

    }

    public void addRightView(View view) {
        rightLayout.addView(view, 0);
    }

    public void addLeftView(View view) {
        leftLayout.addView(view);
    }

    public ImageView addRightImg(int res) {
        return addRightImg(res, null);
    }

    public TextView addRightText(String str) {
        return addRightText(str, null);
    }

    public ImageView addLeftImg(int res) {
        return addLeftImg(res, null);
    }

    public TextView addLeftText(String str) {
        return addLeftText(str, null);
    }

    public ImageView addRightImg(int res, MenuBuilder.OnMenuClickListener onMenuClickListener) {
        ImageView imageView = (ImageView) new MenuBuilder(getContext(), this)
                .icon(res)
                .listener(onMenuClickListener)
                .createImage();
        addRightView(imageView);
        return imageView;
    }

    public TextView addRightText(String str, MenuBuilder.OnMenuClickListener onMenuClickListener) {
        TextView textView = (TextView) new MenuBuilder(getContext(), this)
                .text(str)
                .listener(onMenuClickListener)
                .createText();
        addRightView(textView);
        return textView;
    }

    public ImageView addLeftImg(int res, MenuBuilder.OnMenuClickListener onMenuClickListener) {
        ImageView imageView = (ImageView) new MenuBuilder(getContext(), this)
                .icon(res)
                .listener(onMenuClickListener)
                .createImage();
        addLeftView(imageView);
        return imageView;
    }

    public TextView addLeftText(String str, MenuBuilder.OnMenuClickListener onMenuClickListener) {
        TextView textView = (TextView) new MenuBuilder(getContext(), this)
                .text(str)
                .listener(onMenuClickListener)
                .createText();
        addLeftView(textView);
        return textView;
    }

}
