package com.sweetieplayer.vod;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import androidx.multidex.MultiDex;
import com.dpuntu.downloader.DownloadManager;
import com.dpuntu.downloader.Downloader;
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
import com.sweetieplayer.vod.bean.PlayScoreBean;
import com.sweetieplayer.vod.bean.StartBean;
import com.sweetieplayer.vod.download.GetFileSharePreance;
import com.tencent.smtt.sdk.QbSdk;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import jaygoo.library.m3u8downloader.M3U8Library;
import org.litepal.LitePal;
import org.xutils.x;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.sweetieplayer.vod.ad3.AdConstants.AppKey;
import static com.sweetieplayer.vod.ad3.AdConstants.userId;

public class App extends BaseApplication {
    private static final String TAG = "App";

    public static List<String> searchHot;
    public static StartBean startBean;
    public static AppConfigBean playAd;
    public static AppConfigBean tagConfig;

    private static WeakReference<App> weakReference;
    private static App vocApp;
    public static List<Downloader> downloaders = new ArrayList<>();

    public static PlayScoreBean curPlayScoreBean;

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
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context);
            }
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

        initAdSet();
        Hawk.init(this).build();
//        Random random = new Random();
//        int ad = random.nextInt(10);
//        if(ad > 6){
//            ZgalaxySDK.getInstance().init(this,"KA-d27e79b934f84169b7584f0c98c0ca10",true);
//        }else{
//            ZgalaxySDK.getInstance().init(this,"BD-f3b83ccb87a340258ec65c154da35b81",true);
//        }
        DownloadManager.initDownloader(vocApp);

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



        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
//                .setLogEnabled(BuildConfig.DEBUG)
                .setPlayerFactory(MyIjkPlayerFactory.create())
                //.setPlayerFactory(ExoMediaPlayerFactory.create(this))
                //.setAutoRotate(true)
//                .setEnableMediaCodec(true)
                //.setUsingSurfaceView(true)
                //.setEnableParallelPlay(true)
                //.setEnableAudioFocus(true)
                //.setScreenScale(VideoView.SCREEN_SCALE_MATCH_PARENT)
                .build());
//        //设置toast的颜色
//        ToastUtils.setBgColor(ContextCompat.getColor(this, R.color.colorAccent));

        M3U8Library.init(this);
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

    public synchronized static GetFileSharePreance getFileSharePreance(){
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

        MultiDex.install(this);
        OSETSDKProtected.install(this);
        OSETSDK.getInstance().setUserId(userId);
        OSETSDK.getInstance().init(this, AppKey, new OSETInitListener() {
            @Override
            public void onError(String s) {
                //初始化失败：会调用不到广告，清选择合适的时机重新进行初始化
            }

            @Override
            public void onSuccess() {
                //初始化成功：可以开始调用广告
            }
        });
//        OSETSDK.getInstance().setYMID(this, "9143");//对接小说要设置这个YMID（找运营要这个YMID）

        Log.e("aaaaaaaadfsdf", "当前版本号：" + Build.VERSION.SDK_INT + "——android O版本号：" + Build.VERSION_CODES.O);
    }

}
