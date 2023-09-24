package com.sweetieplayer.vod;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import androidx.multidex.MultiDex;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.dpuntu.downloader.DownloadManager;
import com.github.StormWyrm.wanandroid.base.exception.ResponseException;
import com.github.StormWyrm.wanandroid.base.net.RequestManager;
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver;
import com.google.gson.Gson;
import com.jiagu.sdk.OSETSDKProtected;
import com.kc.openset.OSETSDK;
import com.kc.openset.listener.OSETInitListener;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.*;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sweetieplayer.av.play.MyIjkPlayerFactory;
import com.sweetieplayer.vod.base.BaseApplication;
import com.sweetieplayer.vod.bean.AppConfigBean;
import com.sweetieplayer.vod.bean.BaseResult;
import com.sweetieplayer.vod.bean.PlayScoreBean;
import com.sweetieplayer.vod.bean.StartBean;
import com.sweetieplayer.vod.download.GetFileSharePreance;
import com.sweetieplayer.vod.netservice.StartService;
import com.sweetieplayer.vod.network.RetryWhen;
import com.sweetieplayer.vod.utils.AgainstCheatUtil;
import com.sweetieplayer.vod.utils.OkHttpUtils;
import com.sweetieplayer.vod.utils.Retrofit2Utils;
import com.tencent.smtt.sdk.QbSdk;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import jaygoo.library.m3u8downloader.M3U8Library;
import okhttp3.Call;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;
import org.xutils.x;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;

import static org.seamless.xhtml.XHTML.ELEMENT.object;

public class App extends BaseApplication {
    private static final String TAG = "App";

    public static List<String> searchHot;
    public static StartBean startBean;
    public static AppConfigBean tagConfig;

    private static WeakReference<App> weakReference;
    private static App vocApp;

    private Disposable disposable;
    private static StartBean.Ads ads;
    public static PlayScoreBean curPlayScoreBean;
    public static boolean isGetAdConf = false;

    public static App getInstance() {
        return weakReference.get();
    }


    public static App getApplication() {
        return vocApp;
    }

    // 页面刷新代码 static 代码段可以防止内存泄露
    static {
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @NonNull
//            @Override
//            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
//                MaterialHeader materialHeader = new MaterialHeader(context);
//                materialHeader.setColorSchemeColors(0xFFFF6600);
//                return materialHeader;
//            }
//        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context);
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            setRxJavaErrorHandler();

        } catch (Exception e) {
        }
        LitePal.initialize(this);
        weakReference = new WeakReference<>(this);
        vocApp = this; //xUtils 初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);//是否输出Debug日志

        Hawk.init(this).build();
        DownloadManager.initDownloader(vocApp);

        init_x5();

        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setPlayerFactory(MyIjkPlayerFactory.create())
                .build());

        M3U8Library.init(this);

        getStartData();
        waitAdConf();
        initAdSet();
    }

    public static int getSrceenWidth() {
        return ((WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }


    // 页面刷新代码 static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static StartBean.Ads getAds() {
        return ads;
    }

    public synchronized static GetFileSharePreance getFileSharePreance() {
        return new GetFileSharePreance(vocApp);
    }

    /**
     * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收，需要提前设置ErrorHandler
     * 详情：http://engineering.rallyhealth.com/mobile/rxjava/reactive/2017/03/15/migrating-to-rxjava-2.html#Error Handling
     */
    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    private void initAdSet() {
        if (ads == null) {
            Log.i(TAG, "ads is null, return");
            return;
        }
        if (ads.getAd_user() == null || ads.getApp_key() == null) {
            Log.i(TAG, "ad_user or ad_app_key is null, return");
            return;
        }
        MultiDex.install(this);
        OSETSDKProtected.install(this);
        OSETSDK.getInstance().setUserId(ads.getAd_user().getDescription());
        OSETSDK.getInstance().init(this, ads.getApp_key().getDescription(), new OSETInitListener() {
            @Override
            public void onError(String s) {
                //初始化失败：会调用不到广告，清选择合适的时机重新进行初始化
            }

            @Override
            public void onSuccess() {
                //初始化成功：可以开始调用广告
            }
        });

        Log.e("aaaaaaaadfsdf", "当前版本号：" + Build.VERSION.SDK_INT + "——android O版本号：" + Build.VERSION_CODES.O);
    }

    private void init_x5() {
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.i(getClass().getName().toString(), "initX5Environment onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.i(getClass().getName().toString(), "initX5Environment onViewInitFinished");
            }
        });
    }

    private void getStartData() {
        Log.i("xxxxxxx", "startbean========001");
        OkHttpUtils.getInstance().getDataAsynFromNet1(ApiConfig.BASE_URL + ApiConfig.getStart, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String retString = response.body().string();
                Type typeReference = new TypeReference<BaseResult<StartBean>>() {
                }.getType();
                BaseResult<StartBean> ret = new Gson().fromJson(retString,typeReference);


                CacheDiskStaticUtils.put(TAG, ret.getData());
                App.ads = ret.getData().getAds();
                isGetAdConf = true;
            }

            @Override
            public void failed(Call call, IOException e) {
                Log.e(TAG, "初始化数据失败:", e);
            }
        });
    }

    private void waitAdConf() {
        long start = System.currentTimeMillis();
        long taken = 10000;
        while (true) {
            if (System.currentTimeMillis() - start > taken) {
                Log.e(TAG, "等待广告配置超过10s，放弃加载广告");
                break;
            }
            if (isGetAdConf) {
                break;
            }

        }
    }
}
