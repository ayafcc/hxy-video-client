package cn.mahua.vod.ui.rank;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;

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
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseMainFragment;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.RankOrderEvent;
import cn.mahua.vod.bean.TypeBean;
import cn.mahua.vod.netservice.TypeService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.ui.home.RankPagerFragmentAdapter;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.DensityUtils;
import cn.mahua.vod.utils.Retrofit2Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RankFragment extends BaseMainFragment {
    @BindView(R.id.tv_title)
    TextView titleTv;
    Disposable disposable1;
    @BindView(R.id.tl_rank)
    TabLayout tl_rank;
    private RankPagerFragmentAdapter pagerFragmentAdapter;
    @BindView(R.id.vp_rank)
    ViewPager vp_rank;
    @BindView(R.id.rl_day)
    RelativeLayout rl_day;
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.iv_show)
    ImageView iv_show;
    private TabLayout.Tab oldTab;
    private View oldView;
    public static volatile int mPosition;
    private boolean isInit = false;
    private int mDay = 1;
    private RankPopup popup;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_rank;
    }

    public static RankFragment newInstance() {
        Bundle args = new Bundle();
        RankFragment fragment = new RankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isInit) getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopGet();
    }

    @Override
    public void onDestroyView() {
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
            disposable1 = null;
        }

        super.onDestroyView();
    }

    private void initView() {
        popup = new RankPopup(getActivity());
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_show.setBackgroundResource(R.drawable.icon_open);
            }
        });
        rl_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popup.isShowing()) {
                    popup.dismiss();
                } else {
                    iv_show.setBackgroundResource(R.drawable.icon_close);
                    popup.showAtLocation(rl_day, Gravity.RIGHT | Gravity.TOP, DensityUtils.INSTANCE.dp2px(getActivity(), 5), DensityUtils.INSTANCE.dp2px(getActivity(), 100));
                }
            }
        });
        vp_rank.setOffscreenPageLimit(1);
        vp_rank.setAdapter(pagerFragmentAdapter = new RankPagerFragmentAdapter(getChildFragmentManager()));
        tl_rank.setupWithViewPager(vp_rank);
        TabLayout.OnTabSelectedListener onTabSelectedListener;
        tl_rank.addOnTabSelectedListener(onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
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
                    view.animate().scaleX(1.1f).scaleY(1.1f).start();
                    mPosition = tab.getPosition();
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
        TabLayout.Tab tab = tl_rank.getTabAt(0);
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
                                vp_rank.setAdapter(pagerFragmentAdapter = new RankPagerFragmentAdapter(getChildFragmentManager()));
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(RankOrderEvent event) {
        mDay = event.rankOrder;
        switch (event.rankOrder) {
            case 1:
                tv_day.setText("全部");
                break;
            case 2:
                tv_day.setText("30天");
                break;
            case 3:
                tv_day.setText("7天");
                break;
            case 4:
                tv_day.setText("1天");
                break;
        }
    }
}
