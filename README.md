##前言
标题栏是app开发经常用到的东西，几乎每个页面都有，那就一定需要一个通用的标题栏了。

说到标题栏，很多人会想到Toolbar，谷歌爸爸的东西，毋庸置疑；可RadioButton你还在用么，为什么？拓展性差，我曾经也封装改造过Toolbar，标题字体颜色、字体大小、标题居中、更改高度、左侧留白等等等等，成功的用了一段时间、但心里还是有些不舒服（别说新手用、面对多变的需求，还是会显得很无力吧）

请不要喷我、因为我曾经也是一个倔强的坚信谷歌爸爸所有东西的Android开发者；
说了这么多、进入正题吧。
##效果及实现
**1.最简单的实现和效果**

xml
```
 <com.next.easytitlebar.view.EasyTitleBar
        android:id="@+id/titleBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"></com.next.easytitlebar.view.EasyTitleBar>
```
Activity
```
/**
 * 历史记录
 */
public class HistoryActivity extends BaseActivity {


    @Override
    protected int getScreenMode() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void onViewCreated() {

    }

    @Override
    protected void initEventAndData() {

    }
}
```
效果
![基础版](https://upload-images.jianshu.io/upload_images/5739496-daf07092b921f949.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Activity中什么操作都没有就完成了这个实现、点击箭头直接返回、标题显示的是AndroidManifest.xml中的label(当然这些都是封装好的、下面会提到)
```
  <activity
            android:name=".ui.HistoryActivity"
            android:label="历史记录"
            android:launchMode="singleTask"
            android:screenOrientation="portrait" />
```
**2.左右两侧新增menu，可能是图片、文字、也可能是一个稍复杂的View（数量也都不一定），这也是经常见的需求**
- Java代码添加文字、图片及监听事件
```
 titleBar.addRightImg(R.mipmap.icon_contact, new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
            @Override
            public void OnMenuEvent() {
                Toast.makeText(getContext(), "没有联系人", Toast.LENGTH_SHORT).show();
            }
        });
```
也可以这样添加menu、同时设置之间的距离
```
 locationText = (TextView) new EasyTitleBar.MenuBuilder(getContext(), titleBar)
                .text("北京")
                .paddingleft(15)
                .paddingright(15)
                .menuTextColor(ContextCompat.getColor(getContext(),R.color.appColor))
                .onItemClickListener(new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
                    @Override
                    public void OnMenuEvent() {
                        locationText.setText("上海");
                    }
                })
                .createText();
titleBar.addLeftView(locationText);
```
- Java代码添加稍复杂view
```
 View view = LayoutInflater.from(getContext()).inflate(R.layout.schedule_menu_view, null);
 titleBar.addRightView(view);
```
- xml中也可以添加(xml左右两边最多添加三个、应该够了吧，满足不了可以在Java代码中添加)
```
 <!--右边图标-->
        <attr name="Easy_rightOneImage" format="reference" />
        <!--右边文字-->
        <attr name="Easy_rightOneText" format="string" />
        <!--右边图标-->
        <attr name="Easy_rightTwoImage" format="reference" />
        <!--右边文字-->
        <attr name="Easy_rightTwoText" format="string" />
        <!--右边图标-->
        <attr name="Easy_rightThreeImage" format="reference" />
        <!--右边文字-->
        <attr name="Easy_rightThreeText" format="string" />
```
- 等等、那点击事件呢？

你可以通过getRightLayout（position）直接获取到view、然后设置点击事件
```
 titleBar.getRightLayout(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //点击了
            }
        });
```
这里需要注意的是右侧添加的view是从右向左排列的
![image.png](https://upload-images.jianshu.io/upload_images/5739496-4acc04fe15a5cd9d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
**3.滑动渐变**

可使用attachScrollView方法（支持NestedScrollView和RecyclerView），当然你也可以自己监听滑动距离、然后调用titleBar.setBackgroundColor(color)设置背景的颜色；
```
titleBar.attachScrollView(mSrollView, R.color.white, EasyUtil.dip2px(getContext(), 250) + titleBar.getHeight(), new EasyTitleBar.OnSrollAlphaListener() {
            @Override
            public void OnSrollAlphaEvent(float alpha) {
                if (alpha > 0.8) {
                    titleBar.setTitle("我的");
                    titleBar.setTitleColor(ContextCompat.getColor(getContext(), R.color.common_text_3));
                    EasyStatusBarUtil.StatusBarLightMode(getActivity(), R.color.white, R.color.status_bar_color); //设置白底黑字
                    isBlack = true;
                } else {
                    isBlack = false;
                    EasyStatusBarUtil.StatusBarDarkMode(getActivity(), ((MainActivity) getActivity()).getMode());
                    titleBar.setTitle("");
                    titleBar.setTitleColor(ContextCompat.getColor(getContext(), R.color.white));
                }
            }
        });
```
**4.双击事件**

处理些滑动到顶部之类的需求
```
 titleBar.setOnDoubleClickListener(new EasyTitleBar.OnDoubleClickListener() {
            @Override
            public void onDoubleEvent(View view) {
                mSrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mSrollView.fling(0);
                       // mSrollView.smoothScrollTo(0, 0);
                        mSrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });

            }
        });
```

##配置
######1. 依赖
Step 1. Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
	implementation 'com.github.forvv231:EasyTitleBar:1.0.0'
```
######2. Application中设置全局默认配置
```
  private void initEasyTitleBar() {
        EasyTitleBar.init()
                .backIconRes(R.mipmap.icon_l)
                .backgroud(ContextCompat.getColor(instance, R.color.appColor))
                .titleSize(18)
                .titleColor(ContextCompat.getColor(instance, R.color.white))
                .titleBarHeight(56);
    }
```

##封装
######**默认点击返回箭头、关闭界面**

参考[我一行代码都不写实现Toolbar!你却还在封装BaseActivity?](https://www.jianshu.com/p/75a5c24174b2)，在Application的onCreate加入以下代码
```
registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {

               // ActivityBean bean = new ActivityBean();
               // Unbinder unbinder = ButterKnife.bind(activity);
               // bean.setUnbinder(unbinder);
              //  activity.getIntent().putExtra("ActivityBean", bean);

                //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
                if (activity.findViewById(R.id.titleBar) != null) { //找到 Toolbar 并且替换 Actionbar
                    EasyTitleBar easyTitleBar = activity.findViewById(R.id.titleBar);
                    easyTitleBar.getBackLayout().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            activity.onBackPressed();
                        }
                    });
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
               // ActivityBean bean = activity.getIntent().getParcelableExtra("ActivityBean");
              //  bean.getUnbinder().unbind();
            }

        });
```
**注：xml使用时id要和Application中findViewById中的一样，代码中屏蔽的为ButterKnife的部分、你也可以尝试下、就不用每次都在Activity中绑定了**
##属性
```
 <declare-styleable name="EasyTitleBar">
        <!--标题栏高度-->
        <attr name="Easy_titleBarHeight" format="dimension" />
        <!--标题栏背景颜色-->
        <attr name="Easy_titleBarBackground" format="color" />
        <!--填充状态栏的颜色-->
        <attr name="Easy_fitColor" format="color|reference" />

        <!--返回箭头、左右viewgroup距两边的距离-->
        <attr name="Easy_parentPadding" format="dimension" />
        <!--左右添加的view之间的距离-->
        <attr name="Easy_viewPadding" format="dimension" />

        <!--标题文字-->
        <attr name="Easy_title" format="string" />
        <!--标题大小-->
        <attr name="Easy_titleSize" format="dimension" />
        <!--标题颜色-->
        <attr name="Easy_titleColor" format="color" />
        <!--标题位置-->
        <attr name="Easy_titleStyle">
            <flag name="center" value="0" />
            <flag name="left" value="1" />
        </attr>

        <!--标题栏分割线的有无-->
        <attr name="Easy_lineState">
            <flag name="gone" value="0" />
            <flag name="visiable" value="1" />
        </attr>
        <!--标题栏分割线高度-->
        <attr name="Easy_lineHeight" format="dimension" />
        <!--标题栏分割线颜色-->
        <attr name="Easy_lineColor" format="color" />


        <!--左边第一个返回箭头的有无-->
        <attr name="Easy_backLayoutState">
            <flag name="visiable" value="1" />
            <flag name="gone" value="0" />
        </attr>
        <!--左边箭头icon-->
        <attr name="Easy_backRes" format="reference" />
        <!--左边图标-->
        <attr name="Easy_leftOneImage" format="reference" />
        <!--左边文字-->
        <attr name="Easy_leftOneText" format="string" />
        <!--左边图标-->
        <attr name="Easy_leftTwoImage" format="reference" />
        <!--左边文字-->
        <attr name="Easy_leftTwoText" format="string" />
        <!--左边图标-->
        <attr name="Easy_leftThreeImage" format="reference" />
        <!--左边文字-->
        <attr name="Easy_leftThreeText" format="string" />
        <!--左边ViewGroup的有无-->
        <attr name="Easy_leftLayoutState">
            <flag name="gone" value="0" />
            <flag name="visiable" value="1" />
        </attr>

        <!--右边图标-->
        <attr name="Easy_rightOneImage" format="reference" />
        <!--右边文字-->
        <attr name="Easy_rightOneText" format="string" />
        <!--右边图标-->
        <attr name="Easy_rightTwoImage" format="reference" />
        <!--右边文字-->
        <attr name="Easy_rightTwoText" format="string" />
        <!--右边图标-->
        <attr name="Easy_rightThreeImage" format="reference" />
        <!--右边文字-->
        <attr name="Easy_rightThreeText" format="string" />
        <!--右边viewGroup的有无-->
        <attr name="Easy_rightLayoutState">
            <flag name="gone" value="0" />
            <flag name="visiable" value="1" />
        </attr>

        <!--设置是否充满状态栏-->
        <attr name="Easy_fitsSystemWindows" format="boolean" />
        <!--设置是否充满状态栏-->
        <attr name="Easy_hasStatusPadding" format="boolean" />


        <!--菜单图标大小-->
        <attr name="Easy_menuImgSize" format="dimension" />
        <!--菜单文字大小-->
        <attr name="Easy_menuTextSize" format="dimension" />
        <!--菜单文字颜色-->
        <attr name="Easy_menuTextColor" format="color" />


    </declare-styleable>
```
---
##Demo
github：[https://github.com/forvv231/EasyTitleBar.git](https://github.com/forvv231/EasyTitleBar.git)

apk：[ https://fir.im/EasyTitleBar]( https://fir.im/EasyTitleBar)

![image.png](https://upload-images.jianshu.io/upload_images/5739496-b8732bd73e37c445.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


---
##By the way
- 状态栏白底黑字以及涉及到Fragment状态栏的处理，参考[白底黑字状态栏（详细使用步骤及各机型测试结果）](https://www.jianshu.com/p/f6a4433cfd0f)

- Demo中快速实现底部导航栏，参考[几行代码实现Android底部导航栏（带加号、红点提示、数字消息）](https://www.jianshu.com/p/ce8e09cda486)

---

##点个赞吧
我为了钱做过最疯狂的事就是

每天都按时上班...

