package com.sweetieplayer.vod.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;

import com.sweetieplayer.vod.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.OnClick;
import com.sweetieplayer.vod.base.BackMainFragment;
import com.sweetieplayer.vod.base.BaseMainFragment;
import com.sweetieplayer.vod.bean.PageResult;
import com.sweetieplayer.vod.bean.TypeBean;
import com.sweetieplayer.vod.netservice.TypeService;
import com.sweetieplayer.vod.network.RetryWhen;
import com.sweetieplayer.vod.ui.down.AllDownloadActivity;
import com.sweetieplayer.vod.ui.login.LoginActivity;
import com.sweetieplayer.vod.ui.score.PlayScoreActivity;
import com.sweetieplayer.vod.ui.screen.ScreenActivity3;
import com.sweetieplayer.vod.ui.seek.SeekActivity;
import com.sweetieplayer.vod.utils.AgainstCheatUtil;
import com.sweetieplayer.vod.utils.LoginUtils;
import com.sweetieplayer.vod.utils.Retrofit2Utils;
import com.sweetieplayer.vod.utils.UserUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseMainFragment implements BackMainFragment {

    @SuppressWarnings("WeakerAccess")
    public static volatile int mPosition;
    private List<TypeBean> typeBeans;

    private boolean isInit = false;

    @BindView(R.id.iv_home_top_bg)
    ImageView iv_home_top_bg;

    @BindView(R.id.tv_home_seek)
    TextView tv_home_seek;
    @BindView(R.id.tv_home_all)
    TextView tv_home_all;
    @BindView(R.id.iv_home_history)
    ImageView iv_home_history;
    @BindView(R.id.iv_home_download)
    ImageView iv_home_download;

    @BindView(R.id.tl_home)
    TabLayout tl_home;
    private PagerFragmentAdapter pagerFragmentAdapter;

    @BindView(R.id.vp_home)
    ViewPager vp_home;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(HomeFragment.this);
        if (!isInit) getData();
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTopBarBg(TopBarEvent event) {
        // iv_home_top_bg.setImageBitmap(event.getTopBarBg());
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(HomeFragment.this);
        super.onPause();
        stopGet();
    }

    private View oldView;
    private TabLayout.Tab oldTab;

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void initView() {
        vp_home.setAdapter(pagerFragmentAdapter = new PagerFragmentAdapter(getChildFragmentManager(), "推荐"));
        tl_home.setupWithViewPager(vp_home);
        TabLayout.OnTabSelectedListener onTabSelectedListener;
        tl_home.addOnTabSelectedListener(onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.e("选项卡切换！" + tab.toString());
                if (tab != oldTab) {
                    oldTab = tab;
                    if (oldView != null) oldView.animate().scaleX(1).scaleY(1).start();
                    LinearLayout linearLayout = tab.view;
                    linearLayout.setClipChildren(false);
                    linearLayout.setClipToPadding(false);
                    View view = linearLayout.getChildAt(1);
                    view.animate().scaleX(1.3f).scaleY(1.3f).start();
                    mPosition = tab.getPosition();
                    if (mPosition == 0) {
                        iv_home_history.setVisibility(View.VISIBLE);
                        iv_home_download.setVisibility(View.VISIBLE);
                        tv_home_all.setVisibility(View.GONE);
                    } else {
                        iv_home_history.setVisibility(View.GONE);
                        iv_home_download.setVisibility(View.GONE);
                        tv_home_all.setVisibility(View.VISIBLE);
                    }
                    oldView = view;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayout.Tab tab = tl_home.getTabAt(0);
        if (tab != null) {
            onTabSelectedListener.onTabSelected(tab);
        }
    }

    private Disposable disposable;

    private void getData() {
        isInit = false;
        TypeService typeService = Retrofit2Utils.INSTANCE.createByGson(TypeService.class);
        if (AgainstCheatUtil.showWarn(typeService)) {
            return;
        }
        typeService.getTypeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<TypeBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                            disposable = null;
                        }
                        disposable = d;
                    }

                    @Override
                    public void onNext(PageResult<TypeBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                isInit = true;
                                List<TypeBean> list = result.getData().getList();
                                List<TypeBean> newList = new ArrayList<>();
                                ArrayList<Integer> sortList = new ArrayList<>();
                                for (TypeBean bean : list) {
                                    sortList.add(bean.getType_sort());
                                }
                                Collections.sort(sortList);

                                for (int i = 0; i < sortList.size(); i++) {
                                    for (int j = 0; j < list.size(); j++) {
                                        TypeBean bean = list.get(j);
                                        if (sortList.get(i) == bean.getType_sort()) {
                                            newList.add(bean);
                                        }
                                    }
                                }
                                typeBeans = newList;
                                if (pagerFragmentAdapter != null) {
                                    pagerFragmentAdapter.addData(newList);
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
                    }
                });
    }

    private void stopGet() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    @OnClick(R.id.tv_home_all)
    void allScreen() {
        if (typeBeans != null && (mPosition - 1) < typeBeans.size()) {
            ScreenActivity3.start("", typeBeans.get(mPosition - 1));
        }
    }

    @OnClick(R.id.iv_home_history)
    void playScore() {
        if (UserUtils.isLogin()) {
            ActivityUtils.startActivity(PlayScoreActivity.class);
        } else {
            ActivityUtils.startActivity(LoginActivity.class);
        }
    }

    @OnClick(R.id.tv_home_seek)
    void clickSeek() {
        ActivityUtils.startActivity(SeekActivity.class);
    }

    @OnClick(R.id.iv_home_download)
    void clickDownload() {
        if (LoginUtils.checkLogin(getActivity())) {
            AllDownloadActivity.Companion.start(getActivity());
        }

    }


    @Override
    public boolean onBackPressedSupport() {
        if (tl_home != null && tl_home.getSelectedTabPosition() != 0) {
            TabLayout.Tab tab = tl_home.getTabAt(0);
            if (tab != null) {
                tab.select();
                return true;
            }
        }
        return super.onBackPressedSupport();
    }

}
