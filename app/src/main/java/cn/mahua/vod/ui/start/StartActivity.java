package cn.mahua.vod.ui.start;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.github.StormWyrm.wanandroid.base.exception.ResponseException;
import com.github.StormWyrm.wanandroid.base.net.RequestManager;
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;



import pro.dxys.fumiad.FuMiAd;
import pro.dxys.fumiad.FuMiSplashListener;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.App;
import cn.mahua.vod.MainActivity;
import cn.mahua.vod.R;
import cn.mahua.vod.ad.AdClickListener;
import cn.mahua.vod.ad.AdWebView;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.bean.AppConfigBean;
import cn.mahua.vod.bean.BaseResult;
import cn.mahua.vod.bean.CloseSplashEvent;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.SpecialtTopicBean;
import cn.mahua.vod.bean.StartBean;
import cn.mahua.vod.download.SPKey;
import cn.mahua.vod.netservice.StartService;
import cn.mahua.vod.netservice.TopicService;
import cn.mahua.vod.netservice.VodService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.OkHttpUtils;
import cn.mahua.vod.utils.Retrofit2Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Response;

public class StartActivity extends BaseActivity {

    public static final String KEY_START_BEAN = "KEY_START_BEAN";

    private String ad_str;
    private int ad_str_status;
    private Disposable disposable;
    private StartService startService;
    private Disposable disposable1;
    //private FrameLayout mAdviceLayout;
    @BindView(R.id.awv_start)
    AdWebView webView;
    @BindView(R.id.tv_start)
    TextView textView;
    @BindView(R.id.iv_image)
    ImageView imageView;
    @BindView(R.id.tv_load)
    TextView loadTv;
    @BindView(R.id.advertLayout)
    FrameLayout mAdviceLayout;

    private boolean isInit = false;
    private static final int MAX_TIME = 5;
    private int start_time = MAX_TIME;
    private Handler handler = new Handler();
    private boolean isClosed = false;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setTime(start_time);
            start_time -= 1;
            if (start_time >= 0 && !isClosed) {
                handler.postDelayed(runnable, 1000);
            } else {
                gotoMain();
            }
        }
    };

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        //清空启动背景
        //this.getWindow().getDecorView().setBackground(null);
        super.onCreate(savedInstanceState);
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
        if (!isInit) getStartData();
        // getTopicData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getStartData() {
        Log.i("xxxxxxx", "startbean========001");
        if (startService == null) {
            startService = Retrofit2Utils.INSTANCE.createByGson(StartService.class);
        }
        if (AgainstCheatUtil.showWarn(startService)) {
            return;
        }
        startService.getStartBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 30))
                .subscribe(new Observer<BaseResult<StartBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("xxxxxxx", "disposable========haha111");
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                            disposable = null;
                        }
                        disposable = d;
                    }

                    @Override
                    public void onNext(BaseResult<StartBean> result) {
                        Log.i("xxxxxxx", "startbean========haha111");
                        if (result != null) {
                            Log.i("xxxxxxx", "startbean========111");
                            if (result.isSuccessful()) {
                                Log.i("xxxxxxx", "startbean========222");
                                if (result.getData() != null) {
                                    Log.i("xxxxxxx", "startbean========333");
                                    StartBean startBean = result.getData();
                                    CacheDiskStaticUtils.put(KEY_START_BEAN, startBean);
                                    if (startBean != null) {
                                        StartBean.Ads ads = startBean.getAds();
                                        if (ads != null) {
                                            StartBean.Ad ad = ads.getStartup_adv();
                                            if (ad != null ) {
                                                StartBean.Ad adconfig = ads.getStartup_config();
                                                if(adconfig.getStatus() == 1){

                                                }else{
                                                    System.exit(0);
                                                }
                                                if(ad.getStatus() == 0 ||ad.getStatus() == 1) {
                                                    ad_str_status = ad.getStatus();
                                                    ad_str = ad.getDescription();
                                                }
                                            }
                                        }
                                        StartBean.Ads newAds = startBean.getAds();
                                        StartBean.Ads oldAds = new StartBean.Ads();
                                        Gson gson = new Gson();

                                        String indexGet = SPUtils.getInstance().getString(SPKey.AD_INDEX);
                                        StartBean.Ad oldIndex = gson.fromJson(indexGet, StartBean.Ad.class);
                                        String cartoonGet = SPUtils.getInstance().getString(SPKey.AD_CARTOON);
                                        StartBean.Ad oldCartoon = gson.fromJson(cartoonGet, StartBean.Ad.class);
                                        String sitcomGet = SPUtils.getInstance().getString(SPKey.AD_SITCOM);
                                        StartBean.Ad oldSitcom = gson.fromJson(sitcomGet, StartBean.Ad.class);
                                        String vodGet = SPUtils.getInstance().getString(SPKey.AD_VOD);
                                        StartBean.Ad oldVod = gson.fromJson(vodGet, StartBean.Ad.class);
                                        String searcherGet = SPUtils.getInstance().getString(SPKey.AD_SEARCHER);
                                        StartBean.Ad oldSearcher = gson.fromJson(searcherGet, StartBean.Ad.class);
                                        String varietyGet = SPUtils.getInstance().getString(SPKey.AD_VARIETY);
                                        StartBean.Ad oldVariety = gson.fromJson(varietyGet, StartBean.Ad.class);

                                        oldAds.setIndex(oldIndex);
                                        oldAds.setCartoon(oldCartoon);
                                        oldAds.setSitcom(oldSitcom);
                                        oldAds.setVod(oldVod);
                                        oldAds.setSearcher(oldSearcher);
                                        oldAds.setVariety(oldVariety);

                                        App.startBean = startBean;
                                        if (newAds != null) {
                                            StartBean.Ad index = newAds.getIndex();
                                            if (newAds.getIndex().getDescription() == null || newAds.getIndex().getDescription().isEmpty()) {
                                                if (oldAds.getIndex() != null && oldAds.getIndex().getDescription() != null && !oldAds.getIndex().getDescription().isEmpty()) {
                                                    index.setDescription(oldAds.getIndex().getDescription());
                                                }
                                            }
                                            String indexStr = gson.toJson(newAds.getIndex(), StartBean.Ad.class);
                                            SPUtils.getInstance().put(SPKey.AD_INDEX, indexStr);


                                            StartBean.Ad cartoon = newAds.getCartoon();
                                            if (newAds.getCartoon().getDescription() == null || newAds.getCartoon().getDescription().isEmpty()) {
                                                if (oldAds.getCartoon() != null && oldAds.getCartoon().getDescription() != null && !oldAds.getCartoon().getDescription().isEmpty()) {
                                                    cartoon.setDescription(oldAds.getCartoon().getDescription());
                                                }
                                            }
                                            String cartoonStr = gson.toJson(newAds.getCartoon(), StartBean.Ad.class);
                                            SPUtils.getInstance().put(SPKey.AD_CARTOON, cartoonStr);


                                            StartBean.Ad sitcom = newAds.getSitcom();
                                            if (newAds.getSitcom().getDescription() == null || newAds.getSitcom().getDescription().isEmpty()) {
                                                if (oldAds.getSitcom() != null && oldAds.getSitcom().getDescription() != null && !oldAds.getSitcom().getDescription().isEmpty()) {
                                                    sitcom.setDescription(oldAds.getSitcom().getDescription());
                                                }
                                            }
                                            String sitcomStr = gson.toJson(newAds.getSitcom(), StartBean.Ad.class);
                                            SPUtils.getInstance().put(SPKey.AD_SITCOM, sitcomStr);


                                            StartBean.Ad vod = newAds.getVod();
                                            if (newAds.getVod().getDescription() == null || newAds.getVod().getDescription().isEmpty()) {
                                                if (oldAds.getVod() != null && oldAds.getVod().getDescription() != null && !oldAds.getVod().getDescription().isEmpty()) {
                                                    vod.setDescription(oldAds.getVod().getDescription());
                                                }
                                            }
                                            String vodStr = gson.toJson(newAds.getVod(), StartBean.Ad.class);
                                            SPUtils.getInstance().put(SPKey.AD_VOD, vodStr);


                                            StartBean.Ad searcher = newAds.getSearcher();
                                            if (newAds.getSearcher().getDescription() == null || newAds.getSearcher().getDescription().isEmpty()) {
                                                if (oldAds.getSearcher() != null && oldAds.getSearcher().getDescription() != null && !oldAds.getSearcher().getDescription().isEmpty()) {
                                                    searcher.setDescription(oldAds.getSearcher().getDescription());
                                                }
                                            }
                                            String searcherStr = gson.toJson(newAds.getSearcher(), StartBean.Ad.class);
                                            SPUtils.getInstance().put(SPKey.AD_SEARCHER, searcherStr);


                                            StartBean.Ad variety = newAds.getVariety();
                                            if (newAds.getVariety().getDescription() == null || newAds.getVariety().getDescription().isEmpty()) {
                                                if (oldAds.getVariety() != null && oldAds.getVariety().getDescription() != null && !oldAds.getVariety().getDescription().isEmpty()) {
                                                    variety.setDescription(oldAds.getVariety().getDescription());
                                                }
                                            }
                                            String varietyStr = gson.toJson(newAds.getVariety(), StartBean.Ad.class);
                                            SPUtils.getInstance().put(SPKey.AD_VARIETY, varietyStr);
                                            if (App.startBean != null) {
                                                App.startBean.setAds(newAds);
                                            }
                                        }

                                        App.searchHot = startBean.getSearch_hot();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("xxxxxxx", "startbean========555" + e.getMessage());
                        init();
                    }

                    @Override
                    public void onComplete() {
                        init();
                    }
                });
    }
    /**
     * 付米广告
     * 58rf.cn
     */
  //  public void splashShow(){
    //    FuMiAd.showSplash(this,mAdviceLayout, new FuMiSplashListener(){
    //        @Override
       //     public void onAdShow() {

        //    }

         //   @Override
        //    public void onAdClick() {
       //         gotoMain();
      //          Log.e("tag", "onAdClick");
     //       }

       //     @Override
       //     public void onComplete(boolean success, String errorMsg) {
       //         Log.e("tag", "onComplete");
       //         gotoMain();
       //         finish();
      //      }
   //     });
 //
    /**
     * 广告
     * 58rf.cn
     */
//    public void splashShow(){
//        SplashSdk.getInstance(this).loadSplashAdvert(mAdviceLayout, new SplashAdListener() {
//            @Override
//            public void onError(int i, String s) {
//                gotoMain();
//            }
//            @Override
//            public void onAdClick() {
//                gotoMain();
//            }
//
//            @Override
//            public void onAdShow() {
//            }
//
//            @Override
//            public void onAdSkip() {
//                gotoMain();
//            }
//
//            @Override
//            public void onAdTimeOver() {
//                gotoMain();
//            }
//
//            @Override
//            public void onAdvertStatusClose() {
//                gotoMain();
//            }
//
//
//        });
//    }
    private void stopGet() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable.dispose();
        }
    }
    private  void get_base_url(){
        OkHttpUtils.getInstance()
                .getDataAsynFromNet(ApiConfig.codeurl, new OkHttpUtils.MyNetCall() {
                            @Override
                            public void success(Call call, Response response) throws IOException {
                                boolean isSuccessful = response.isSuccessful();
                                if (isSuccessful) {
                                    LogUtils.d("", "====Parse jiexi2 url="+response.body().string());
                                    //  ApiConfig.BASE_URL = response.body().string();
                                }
                            }

                            @Override
                            public void failed(Call call, IOException e) {

                            }

                        }

                );
    }
//    private void adinit() {
//        String ad_key ="58rf";
//        if(ad_str_status == 1 && StringUtils.isEmpty(ad_str)) {
//            isInit = true;
//            try {
//                splashShow();
//            } catch (Exception e) {
//                gotoMain();
//            }
//        }else if(ad_str_status == 1 && !StringUtils.isEmpty(ad_str)){
//            if(ad_key.equals(ad_str)){
//                splashShow();// 广告弹窗轰炸  描述为特定值
//            }else{   //后台广告  描述不为空
//                init();
//            }
//        }else if(ad_str_status == 0){
//            init();
//        }
//    }
    private void init() {

        if (StringUtils.isEmpty(ad_str) || ad_str_status == 0) {
            gotoMain();
        }

        webView.setVisibility(View.VISIBLE);
        LogUtils.e(ad_str);

        isInit = true;
        webView.isforceFullScreen(true);
        webView.addAdClickListener(new AdClickListener() {
            @Override
            public void onAdClick(String url) {
                isClosed=true;
                handler.removeCallbacks(runnable);
            }
        });
        webView.loadHtmlBody(ad_str);

    }

    private void cancleImage() {
        ValueAnimator anim = ValueAnimator.ofFloat(1, 0);
        anim.setDuration(500);
        anim.setRepeatCount(0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (imageView != null) {
                    imageView.setAlpha((Float) animation.getAnimatedValue());
                    loadTv.setAlpha((Float) animation.getAnimatedValue());
                }
                System.out.println("onAnimationUpdate " + (Float) animation.getAnimatedValue());
            }
        });
        anim.start();
    }

    private void setTime(int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (textView != null) {
                    textView.setText(StringUtils.getString(R.string.skip, i));
                }
            }
        });
    }

    private void gotoMain() {
        isClosed = true;
        handler.removeCallbacks(runnable);
        stopGet();
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }

    @OnClick(R.id.tv_start)
    void missAd() {
        isClosed = true;
        handler.removeCallbacks(runnable);
        stopGet();
        ActivityUtils.startActivity(MainActivity.class);
        finish();
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
                        App.playAd = data;
                    }

                    @Override
                    public void onError(@NotNull ResponseException e) {

                    }
                }
        );

        //获取标签状态
        RequestManager.execute(
                this, vodService.getPlayAd(1 + ""),
                new BaseObserver<AppConfigBean>() {
                    @Override
                    public void onSuccess(AppConfigBean data) {
                        if(data !=null) {
                            App.tagConfig = data;
                        }
                    }

                    @Override
                    public void onError(@NotNull ResponseException e) {

                    }
                }
        );
    }

    private void getTopicData() {
        TopicService cardService = Retrofit2Utils.INSTANCE.createByGson(TopicService.class);
        if (AgainstCheatUtil.showWarn(cardService)) {
            return;
        }
        cardService.getTopicList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<SpecialtTopicBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable1 != null && !disposable1.isDisposed()) {
                            disposable1.dispose();
                            disposable1 = null;
                        }
                        disposable1 = d;
                    }

                    @Override
                    public void onNext(PageResult<SpecialtTopicBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                List<SpecialtTopicBean> list = result.getData().getList();
//                                topicEntities.clear();
//                                topicEntities.addAll(list);
//                                activityLevelAdpter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCloseEvent(CloseSplashEvent event) {
        start_time = MAX_TIME;
        setTime(start_time);
        handler.postDelayed(runnable, 1000);
        cancleImage();
    }

}
