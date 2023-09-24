package com.sweetieplayer.vod.ui.start;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.github.StormWyrm.wanandroid.base.exception.ResponseException;
import com.github.StormWyrm.wanandroid.base.net.RequestManager;
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver;
import com.kc.openset.OSETListener;
import com.kc.openset.OSETSplash;
import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.App;
import com.sweetieplayer.vod.MainActivity;
import com.sweetieplayer.vod.R;
import com.sweetieplayer.vod.base.BaseActivity;
import com.sweetieplayer.vod.bean.AppConfigBean;
import com.sweetieplayer.vod.bean.CloseSplashEvent;
import com.sweetieplayer.vod.bean.StartBean;
import com.sweetieplayer.vod.netservice.StartService;
import com.sweetieplayer.vod.netservice.VodService;
import com.sweetieplayer.vod.utils.AgainstCheatUtil;
import com.sweetieplayer.vod.utils.OkHttpUtils;
import com.sweetieplayer.vod.utils.Retrofit2Utils;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Response;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class StartActivity extends BaseActivity {

    public static final String KEY_START_BEAN = "KEY_START_BEAN";
    private FrameLayout adLayout;
    private Activity thisActivity;

    private boolean isInit = false;
    private static final int MAX_TIME = 5;
    private int start_time = MAX_TIME;
    private final Handler handler = new Handler();
    private boolean isClosed = false;

    private boolean isOnPause = false;//判断是否跳转了广告落地页
    private boolean isClick = false;//是否进行了点击
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        //清空启动背景
        //this.getWindow().getDecorView().setBackground(null);
        thisActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        ToastUtils.showShort("正在选择加速通道，请稍后...");
        EventBus.getDefault().register(this);
        getAppConfig();
        BarUtils.setStatusBarVisibility(this, false);
        BarUtils.setNavBarVisibility(this, false);
        //setContentView(R.layout.activity_start);
        //mAdviceLayout = findViewById(R.id.advertLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("xxxxxxx", "startbean========001onResume");
        if (!isInit) {
            adLayout = findViewById(R.id.ad_set);
            load_ad();
        }

    }


    @Override
    protected void onDestroy() {
        OSETSplash.getInstance().destroy();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void cancleImage() {
        ValueAnimator anim = ValueAnimator.ofFloat(1, 0);
        anim.setDuration(500);
        anim.setRepeatCount(0);
        anim.addUpdateListener(animation -> {
            System.out.println("onAnimationUpdate " + animation.getAnimatedValue());
        });
        anim.start();
    }

    private void setTime(int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                if (textView != null) {
//                    textView.setText(StringUtils.getString(R.string.skip, i));
//                }
            }
        });
    }

    private void gotoMain() {
        isClosed = true;
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }

    private void gotoMainByDefaultSplash() {
        adLayout.setVisibility(View.GONE);
        findViewById(R.id.lunch_bg_top).setVisibility(View.VISIBLE);
        gotoMain();
    }

    public void getAppConfig() {
        VodService vodService = Retrofit2Utils.INSTANCE.createByGson(VodService.class);
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        RequestManager.execute(
                this, vodService.getPlayAd(2 + ""),
                new BaseObserver<AppConfigBean>() {
                    @Override
                    public void onSuccess(AppConfigBean data) {
//                        App.playAd = data;
                    }

                    @Override
                    public void onError(@NotNull ResponseException e) {

                        e.printStackTrace();
                    }
                }
        );

        //获取标签状态
        RequestManager.execute(
                this, vodService.getPlayAd(1 + ""),
                new BaseObserver<AppConfigBean>() {
                    @Override
                    public void onSuccess(AppConfigBean data) {
                        if (data != null) {
                            App.tagConfig = data;
                        }
                    }

                    @Override
                    public void onError(@NotNull ResponseException e) {

                        e.printStackTrace();
                    }
                }
        );
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCloseEvent(CloseSplashEvent event) {
        start_time = MAX_TIME;
        setTime(start_time);
        cancleImage();
    }

    private void load_ad() {
        StartBean.Ads ads = App.getAds();
        if (ads == null) {
            Log.i(KEY_START_BEAN, "ads is null, go to default splash now");
            gotoMainByDefaultSplash();
            return;
        }
        StartBean.Ad splashAd = ads.getStartup_adv();
        if (splashAd == null) {
            Log.i(KEY_START_BEAN, "splash ad is null, go to default splash now");
            gotoMainByDefaultSplash();
            return;
        }
        OSETSplash.getInstance().show(thisActivity, adLayout, splashAd.getDescription(), new OSETListener() {
            @Override
            public void onShow() {
                Log.e(KEY_START_BEAN, "onShow");
            }

            @Override
            public void onError(String s, String s1) {
                Log.e(KEY_START_BEAN, "onError——————code:" + s + "----message:" + s1);
                gotoMainByDefaultSplash();
            }

            @Override
            public void onClick() {
                isClick = true;
                Log.e(KEY_START_BEAN, "onClick");
            }

            @Override
            public void onClose() {
                Log.e(KEY_START_BEAN, "onclose +isOnPause=" + isOnPause + "isClick=" + isClick);
                if (!isOnPause) {//如果已经调用了onPause说明已经跳转了广告落地页
                    gotoMain();
                }
            }
        });
    }

}
