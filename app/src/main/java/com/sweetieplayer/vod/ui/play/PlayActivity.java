package com.sweetieplayer.vod.ui.play;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.sweetieplayer.av.play.AvVideoController;
import com.sweetieplayer.av.play.AvVideoView;
import com.sweetieplayer.av.play.ControllerClickListener;
import com.sweetieplayer.av.play.HdClickListener;
import com.sweetieplayer.vod.App;
import com.sweetieplayer.vod.R;
import com.sweetieplayer.vod.base.BaseSupportActivity;
import com.sweetieplayer.vod.bean.*;
import com.sweetieplayer.vod.jiexi.BackListener;
import com.sweetieplayer.vod.jiexi.JieXiUtils;
import com.sweetieplayer.vod.netservice.PlayService;
import com.sweetieplayer.vod.network.RetryWhen;
import com.sweetieplayer.vod.ui.home.Vod;
import com.sweetieplayer.vod.utils.AgainstCheatUtil;
import com.sweetieplayer.vod.utils.Retrofit2Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;
import xyz.doikki.videoplayer.player.VideoView;

import java.util.*;
public class PlayActivity extends BaseSupportActivity implements ControllerClickListener, HdClickListener, BackListener, VideoView.OnStateChangeListener {
    public static final String KEY_VOD = "KEY_VOD";
    public static final String KEY_SHOW_PROGRESS = "KEY_SHOW_PROGRESS";

    @BindView(R.id.avv_play)
    AvVideoView videoView;
    @BindView(R.id.rv_play_content)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_actor)
    TextView tvActor;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_play_number)
    TextView tvPlayNumber;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_summary_hint)
    TextView tvSummaryHint;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.scSummary)
    ScrollView scSummary;
    @BindView(R.id.iv_close_intro)
    ImageView ivCloseIntro;


    private AvVideoController controller;
    private MultiTypeAdapter adapter;
    private final List<Object> items = new ArrayList<>();
    private final List<String> urlList = new ArrayList<>();
    private boolean isPlay = false;
    private int urlIndex = 0;
    private Map<String, String> aveaders = new Map<String, String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable Object o) {
            return false;
        }

        @Nullable
        @Override
        public String get(@Nullable Object o) {
            return null;
        }

        @Nullable
        @Override
        public String put(String s, String s2) {
            return null;
        }

        @Nullable
        @Override
        public String remove(@Nullable Object o) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends String> map) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<String> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<String, String>> entrySet() {
            return null;
        }
    };


    private VodBean mVodBean;
    private Disposable disposable;

    public static void startByVod(Vod vod) {
        if (vod.getVod_copyright() == 1) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(vod.getVod_jumpurl()));
            ActivityUtils.startActivity(intent);
        } else {
            Intent intent = new Intent(App.getInstance(), NewPlayActivity.class);
            intent.putExtra(KEY_VOD, vod);
            ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.no_anim);
        }
    }

    public static void startByCollection(int vodId) {
        VodBean vodBean = new VodBean();
        vodBean.setVod_id(vodId);
        Intent intent = new Intent(App.getInstance(), NewPlayActivity.class);
        intent.putExtra(KEY_VOD,vodBean);
        ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.no_anim);
    }

    public static void startByPlayScore(int vodId) {
        VodBean vodBean = new VodBean();
        vodBean.setVod_id(vodId);
        Intent intent = new Intent(App.getInstance(), NewPlayActivity.class);
        intent.putExtra(KEY_VOD,vodBean);
        intent.putExtra(KEY_SHOW_PROGRESS,true);
        ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.no_anim);
    }

    public static void startByPlayScoreResult(Fragment content, int vodId) {
        VodBean vodBean = new VodBean();
        vodBean.setVod_id(vodId);
        Intent intent = new Intent(App.getInstance(), NewPlayActivity.class);
        intent.putExtra(KEY_VOD,vodBean);
        intent.putExtra(KEY_SHOW_PROGRESS,true);
     //   ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.no_anim);
        content.startActivityForResult(intent, 1);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_play;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this, Color.BLACK);
        Intent intent = getIntent();
        if (intent != null) {
            mVodBean = (VodBean) intent.getSerializableExtra(KEY_VOD);
        }
        if (mVodBean == null) {
            finish();
            return;
        }
        initView();
        getPlayInfoData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    protected void onPause() {

        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onDestroy() {
        stopData();
        JieXiUtils.INSTANCE.stopGet();
        videoView.release();
        controller.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressedSupport() {
        if (!videoView.onBackPressed()) {
            super.onBackPressedSupport();
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_av_back) {
            finish();
        }
    }

    @Override
    public void switchHd(String url) {

    }

    ///解析回调
    @Override
    public void onSuccess(String url, int curParseIndex, Map<String, String> headers) {
        LogUtils.e(url);
        urlList.add(url);
        aveaders = headers;
        if (!isPlay) {
            controller.hideJiexi();
            play(url);
            Log.i("playactv", "请求的url：" + url);
            isPlay = true;
        }
    }


    @Override
    public void onError() {

    }

    @Override
    public void onProgressUpdate(String msg) {

    }


    ///播放器监听
    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void onPlayStateChanged(int playState) {
        //ToastUtils.showShort(playState);
        if (playState == -1) {
            urlIndex = urlIndex + 1;
            if (urlIndex < urlList.size()) {
                play(urlList.get(urlIndex));
            } else {
                urlIndex = 0;
                videoView.postDelayed(() -> play(urlList.get(urlIndex)), 500);
            }
        }else if (playState == 4) {
//            ToastUtils.showShort("play点击了暂停");
//            if (App.startBean != null) {
//                if (App.startBean!=null&&App.startBean.getAds() != null && App.startBean.getAds().getPlayer_pause() != null) {
//                    ad = App.startBean.getAds().getPlayer_pause();
//                    if (ad != null && !StringUtils.isEmpty(ad.getDescription()) && ad.getStatus() == 1) {
//                        String data = ad.getDescription();
//                        sourceStrArray = data.split(",");
//                        ToastUtils.showShort(sourceStrArray[1]);
//                        controller.showAd(sourceStrArray[1], sourceStrArray[0]);
//                    }
//                }
//            }

        }
    }
    public  String m3u8down() {
            return urlList.get(0);

    }
    private void play(String url) {
        videoView.post(new Runnable() {
            @Override
            public void run() {
                videoView.release();
                if (url.startsWith("//")) {
                    videoView.setUrl("https:" + url,aveaders);
                } else {
                    videoView.setUrl(url,aveaders);
                }
                videoView.start();
            }
        });
    }

    private void title(String string) {
        if (controller != null) {
            controller.post(new Runnable() {
                @Override
                public void run() {
                    controller.setTitle(string);
                }
            });
        }
    }

    private void initView() {
        controller = new AvVideoController(videoView, this);
        controller.setControllerClickListener(this);
        videoView.setHdClickListener(this);
        //设置控制器，如需定制可继承BaseVideoController
        videoView.setVideoController(controller);
        videoView.setOnStateChangeListener(this);
        videoView.setVideoSpeed(SPUtils.getInstance().getInt(AvVideoController.KEY_SPEED_INDEX,3));

        title(mVodBean.getVodName());
        initIntro();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MultiTypeAdapter();
        adapter.register(VodBean.class, new PlayInfoViewBinder().setPlayerInfoClickListener(new PlayInfoViewBinder.PlayerInfoClickListener() {
            @Override
            public void onSummary(View view) {
                recyclerView.setVisibility(View.GONE);
                scSummary.setVisibility(View.VISIBLE);
            }
        }));
        adapter.register(RecommendBean.class, new RecommendViewBinder().setOnTypeChangeLisenter(new RecommendViewBinder.OnTypeChangeLisenter() {
            @Override
            public void onTypeChange(int type) {
                if (type == 0) {
                    getsameTypeData(true);
                } else {
                    getSameActorData();
                }
            }
        }));
        recyclerView.setAdapter(adapter);
    }

    private void initIntro() {
        tvTitle.setText(mVodBean.getVodName());
        tvYear.setText("年代：" + mVodBean.getVod_year());
        tvActor.setText("主演：" + mVodBean.getVod_actor());
        tvType.setText("类型：" + mVodBean.getType().getTypeName());
        tvStatus.setText("状态：" + mVodBean.getVodRemarks());
        tvYear.setText("播放：" + mVodBean.getVod_hits() + "次");
        tvActor.setText("评分：" + mVodBean.getVod_score());
        tvSummary.setText(mVodBean.getVod_blurb());
        ivCloseIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                scSummary.setVisibility(View.GONE);
            }
        });
    }

    private void getPlayInfoData() {
        PlayService playService = Retrofit2Utils.INSTANCE.createByGson(PlayService.class);
        if (AgainstCheatUtil.showWarn(playService)) {
            return;
        }
        playService.getVod(mVodBean.getVod_id(), 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<BaseResult<VodBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                            disposable = null;
                        }
                        disposable = d;
                    }

                    @Override
                    public void onNext(BaseResult<VodBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                VodBean vodBean = result.getData();
                                if (vodBean != null) {
                                    mVodBean = vodBean;
                                    items.add(mVodBean);
                                    adapter.setItems(items);
                                    adapter.notifyDataSetChanged();
                                    parseData(vodBean);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        getsameTypeData(false);
                    }
                });
    }
    private void getsameTypeData(boolean isChange) {
        PlayService playService = Retrofit2Utils.INSTANCE.createByGson(PlayService.class);
        if (AgainstCheatUtil.showWarn(playService)) {
            return;
        }
        playService.getSameTypeList(mVodBean.getType_id(), mVodBean.getVod_class(), 1, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(2, 3))
                .subscribe(new Observer<PageResult<VodBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PageResult<VodBean> result) {
                        if (result != null) {
                            List<VodBean> vodBeans = result.getData().getList();
                            if (result.isSuccessful()) {
                                items.add(new RecommendBean(vodBeans, 0));
                                adapter.setItems(items);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getSameActorData() {
        PlayService playService = Retrofit2Utils.INSTANCE.createByGson(PlayService.class);
        if (AgainstCheatUtil.showWarn(playService)) {
            return;
        }
        playService.getSameActorList(mVodBean.getVod_id(), mVodBean.getVod_actor(), 1, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(2, 3))
                .subscribe(new Observer<PageResult<VodBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PageResult<VodBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                List<VodBean> vodBeans = result.getData().getList();

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getCommentList() {

    }

    private void stopData() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    private void parseData(final VodBean vodBean) {
        controller.showJiexi();
        String s = "";
        String u = "https://v.qq.com/x/cover/lvjgpbrmo0dpz14.html";
        // 开始解析地址
        List<PlayFromBean> fromBeanList = vodBean.getVod_play_list();
        if (fromBeanList != null) {
            for (PlayFromBean fromBean : fromBeanList) {
                try {
                    s = fromBean.getPlayer_info().getParse();
                    u = fromBean.getUrls().get(0).getUrl();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                JieXiUtils.INSTANCE.getPlayUrl(s, u, this);
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
