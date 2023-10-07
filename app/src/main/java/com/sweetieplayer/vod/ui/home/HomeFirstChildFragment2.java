package com.sweetieplayer.vod.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

import com.sweetieplayer.vod.R;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import com.sweetieplayer.vod.App;
import com.sweetieplayer.vod.ads.AdViewBinder;
import com.sweetieplayer.vod.banner.BannerItemViewBinder;
import com.sweetieplayer.vod.banner.BlurBanner;
import com.sweetieplayer.vod.base.BaseItemFragment;
import com.sweetieplayer.vod.bean.BannerBean;
import com.sweetieplayer.vod.bean.Page;
import com.sweetieplayer.vod.bean.PageResult;
import com.sweetieplayer.vod.bean.RecommendBean2;
import com.sweetieplayer.vod.bean.StartBean;
import com.sweetieplayer.vod.bean.TopBean;
import com.sweetieplayer.vod.bean.UpdateEvent;
import com.sweetieplayer.vod.bean.VodBean;
import com.sweetieplayer.vod.card.CardItemViewBinder2;
import com.sweetieplayer.vod.netservice.BannerService;
import com.sweetieplayer.vod.netservice.CardService;
import com.sweetieplayer.vod.netservice.TopService;
import com.sweetieplayer.vod.network.RetryWhen;
import com.sweetieplayer.vod.ui.play.PlayActivity;
import com.sweetieplayer.vod.ui.top.TopItemViewBinder;
import com.sweetieplayer.vod.utils.AgainstCheatUtil;
import com.sweetieplayer.vod.utils.DefaultItemAnimator;
import com.sweetieplayer.vod.utils.LoginUtils;
import com.sweetieplayer.vod.utils.Retrofit2Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;

import static net.lucode.hackware.magicindicator.ScrollState.SCROLL_STATE_IDLE;

public class HomeFirstChildFragment2 extends BaseItemFragment<String> {

    @BindView(R.id.srl_home_first_child)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.rv_home_first_child)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MultiTypeAdapter adapter;
    private List<Object> items = null;
    //为了滑动的时候 只发一次空背景
    private boolean isShowFirstItem = true;

    private boolean isStop = false;

    private Page<VodBean> page;
    private List<VodBean> list2;


    @SuppressWarnings("WeakerAccess")
    public static HomeFirstChildFragment2 newInstance(int position, String data) {
        Bundle args = new Bundle();
        //设置当前位置
        args.putInt(key_position, position);
        args.putSerializable(key_data, data);
        HomeFirstChildFragment2 fragment = new HomeFirstChildFragment2();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_home_first_child;
    }

    @Override
    public void onResume() {
        super.onResume();
        isStop = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isStop = true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //下拉刷新
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        SwipeRefreshLayout.OnRefreshListener onRefreshListener;
        refreshLayout.setOnRefreshListener(onRefreshListener = () -> {
            _isShowEnd = false;
            initData();
            getBannerData();
        });
        onRefreshListener.onRefresh();
    }

    private void initView() {
        adapter = new MultiTypeAdapter();
        adapter.register(BannerBean.class, new BannerItemViewBinder().setOnActionListener(new BlurBanner.onBannerActionListener() {
            @Override
            public void onPageChange(int position, Bitmap bitmap) {
                if (HomeFragment.mPosition == _position && !isStop) {
                    EventBus.getDefault().post(new TopBarEvent().setTopBarBg(bitmap));
                }
            }

            @Override
            public void onBannerClick(int position, Object item) {

                if(LoginUtils.checkLogin(getActivity())){
                    if (item instanceof Vod) {
                        Vod vod = (Vod) item;
                        PlayActivity.startByVod(vod);
                    }

                }
//                if (    SPUtils.getBoolean(getActivity(),"isVip")) {
//                    if (item instanceof Vod) {
//                        Vod vod = (Vod) item;
//                        PlayActivity.startByVod(vod);
//                    }
//                } else {
//                    if (BuildConfig.DEBUG) {
//                    } else {
//                        ActivityUtils.startActivity(LoginActivity.class);
//                    }
//                }
            }
        }));
        adapter.register(TopBean.class, new TopItemViewBinder(0).setActionListener(new TopItemViewBinder.TopItemActionListener() {
            @Override
            public void onClickMore(View view) {
                getTopData();
            }

            @Override
            public void onClickChange(View view) {

            }

            @Override
            public void onClickItem(View view, Object item) {
                if(LoginUtils.checkLogin(getActivity())){
                    if (item instanceof Vod) {
                        Vod vod = (Vod) item;
                        PlayActivity.startByVod(vod);
                    }

                }
            }
        }));
        adapter.register(RecommendBean2.class, new CardItemViewBinder2(true, true,true).setActionListener(new CardItemViewBinder2.CardItemActionListener2() {
            @Override
            public void onClickMore(View view, Object name, List<VodBean> list, String title) {
                ArrayList<VodBean> l = new ArrayList<>();
                l.addAll(list);
                HomeFristMoreActivity.start(l, title);
            }

            @Override
            public void onClickChange(View view, Object o) {
            }

            @Override
            public void onClickItem(View view, Object item) {
                //if(LoginUtils.checkLogin(getActivity())){
                    if (item instanceof Vod) {
                        Vod vod = (Vod) item;
                        PlayActivity.startByVod(vod);
                    }

                //}
            }
        }));
        adapter.register(StartBean.Ad.class, new AdViewBinder());
//        adapter.register(EndBean.class, new EndItemViewBinder());
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        //滑动加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findFirstVisibleItemPosition() != 0) {//离开里第一个ItemView
                    if (!isShowFirstItem) {
//                        EventBus.getDefault().post(new TopBarEvent().setTopBarBg(null));
                        isShowFirstItem = true;
                    }
                } else {
                    isShowFirstItem = false;
                }


                if (!recyclerView.canScrollVertically(-1)) {
                    refreshLayout.setEnabled(true);
                } else if (!recyclerView.canScrollVertically(1)) {
                    refreshLayout.setEnabled(true);
                    if (!_isShowEnd) {
//                        items.add(new EndBean("我是有底线的"));
                        adapter.notifyDataSetChanged();
                        _isShowEnd = true;
                    } else {
                        ToastUtils.showShort("已没有更多的数据");
                    }
                } else {
                    refreshLayout.setEnabled(false);
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
                    EventBus.getDefault().postSticky(new UpdateEvent(false));
                    adapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
                } else {
                    EventBus.getDefault().postSticky(new UpdateEvent(true));
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

        });
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        if (items != null) {
            items.clear();
            adapter.notifyDataSetChanged();
        } else {
            adapter.setItems(items = new ArrayList<>());
            adapter.notifyDataSetChanged();
        }
        //初始化top的数据
        topBean = null;
        top_page = 1;
    }

    private void initBannerData(List<VodBean> list) {
        if (list == null) return;
        BannerBean bannerBean = new BannerBean(list);
        items.add(bannerBean);
        adapter.notifyDataSetChanged();
    }

    private TopBean topBean;

    private void initTopData(List<VodBean> list) {
        if (list == null) return;
        if (topBean == null) {
            topBean = new TopBean("每月Top排行榜", list);
            items.add(topBean);
            adapter.notifyDataSetChanged();
            getCardData();
        } else {
            upTopData(list);
        }
    }

    private void upTopData(List<VodBean> list) {
        if (list == null) return;
        if (topBean != null) {
            topBean.setVodList(list);
            adapter.notifyDataSetChanged();
        }
    }

    private void initCardData(List<RecommendBean2> list) {
        if (list == null || list.isEmpty()) return;
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                items.add(list.get(0));
                if (App.startBean.getAds() != null && App.startBean.getAds().getIndex() != null) {
                    items.add(App.startBean.getAds().getIndex());
                }
            } else {
                items.add(list.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private Disposable disposable1;
    private Disposable disposable2;
    private Disposable disposable3;

    private void getBannerData() {
        BannerService bannerService = Retrofit2Utils.INSTANCE.createByGson(BannerService.class);
        if (AgainstCheatUtil.showWarn(bannerService)) {
            refreshLayout.setRefreshing(false);
            return;
        }
        refreshLayout.setRefreshing(true);
        bannerService.getBannerList(9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<VodBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable1 != null && !disposable1.isDisposed()) {
                            disposable1.dispose();
                            disposable1 = null;
                        }
                        disposable1 = d;
                    }

                    @Override
                    public void onNext(PageResult<VodBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                List<VodBean> list = result.getData().getList();
                                initBannerData(list);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // getTopData();
                        //不显示排行榜
                        getCardData();
                    }
                });
    }

    private int top_page = 1;

    private void getTopData() {
        TopService topService = Retrofit2Utils.INSTANCE.createByGson(TopService.class);
        if (AgainstCheatUtil.showWarn(topService)) {
            return;
        }
        topService.getTopList("top_month", 3, top_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<VodBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable2 != null && !disposable2.isDisposed()) {
                            disposable2.dispose();
                            disposable2 = null;
                        }
                        disposable2 = d;
                    }

                    @Override
                    public void onNext(PageResult<VodBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                list2 = result.getData().getList();
                                initTopData(list2);
                                Log.i("lxh--", list2.size() + "");
                                top_page = result.getData().getPage() + 1;

                                page = result.getData();
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

    private void getCardData() {
        CardService cardService = Retrofit2Utils.INSTANCE.createByGson(CardService.class);
        if (AgainstCheatUtil.showWarn(cardService)) {
            return;
        }
        cardService.getRecommendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<RecommendBean2>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable3 != null && !disposable3.isDisposed()) {
                            disposable3.dispose();
                            disposable3 = null;
                        }
                        disposable3 = d;
                    }

                    @Override
                    public void onNext(PageResult<RecommendBean2> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                List<RecommendBean2> list = result.getData().getList();
                                initCardData(list);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (refreshLayout != null) refreshLayout.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onDestroyView() {
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
            disposable1 = null;
        }
        if (disposable2 != null && !disposable2.isDisposed()) {
            disposable2.dispose();
            disposable2 = null;
        }
        if (disposable3 != null && !disposable3.isDisposed()) {
            disposable3.dispose();
            disposable3 = null;
        }
        super.onDestroyView();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (layoutManager.findFirstVisibleItemPosition() != 0) {
            recyclerView.scrollToPosition(0);
            return true;
        }
        return super.onBackPressedSupport();
    }


}
