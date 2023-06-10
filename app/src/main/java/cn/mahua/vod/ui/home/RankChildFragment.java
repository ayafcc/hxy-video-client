package cn.mahua.vod.ui.home;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import butterknife.BindView;
import cn.mahua.vod.BuildConfig;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseItemFragment;
import cn.mahua.vod.bean.BaseResult;
import cn.mahua.vod.bean.RankBean;
import cn.mahua.vod.bean.RankOrderEvent;
import cn.mahua.vod.bean.UpdateEvent;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.card.RankCardItemViewBinder;
import cn.mahua.vod.download.SPUtils;
import cn.mahua.vod.netservice.VodService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.ui.login.LoginActivity;
import cn.mahua.vod.ui.play.PlayActivity;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.DefaultItemAnimator;
import cn.mahua.vod.utils.LoginUtils;
import cn.mahua.vod.utils.Retrofit2Utils;
import cn.mahua.vod.utils.UserUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;

import static net.lucode.hackware.magicindicator.ScrollState.SCROLL_STATE_IDLE;

public class RankChildFragment extends BaseItemFragment<Type> {

    private String DAY = "vod_hits desc";

    public static RankChildFragment newInstance(int position) {
        Bundle args = new Bundle();
        //设置当前位置
        args.putInt(key_position, position);
        RankChildFragment fragment = new RankChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.srl_rank_child)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.rv_rank_child)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MultiTypeAdapter adapter;
    private List<Object> items = null;
    private boolean isShowFirstItem = true;
    private int mIndex = 1;
    List<VodBean> list = new ArrayList<>();

    private String[] zlass;
    private int zlass_index;


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_rank_child;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mIndex = 1;
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        MaterialHeader header = new MaterialHeader(getActivity());
        header.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorAccent));
        refreshLayout.setRefreshHeader(header);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIndex = 1;
                Bundle args = getArguments();
                getCardData(args.getInt(key_position), DAY);
            }
        });
        refreshLayout.autoRefresh();
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIndex++;
                Bundle args = getArguments();
                getCardData(args.getInt(key_position), DAY);
            }
        });
    }

    private void initView() {
        adapter = new MultiTypeAdapter();
        adapter.register(VodBean.class, new RankCardItemViewBinder(DAY).setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object o = view.getTag();
                //if(LoginUtils.checkLogin(getActivity())){

                        if (o instanceof Vod) {
                            Vod vod = (Vod) o;
                            PlayActivity.startByVod(vod);
                        }

               // }
            }
        }));
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
                    LogUtils.e("滑动到了顶部--" + RankChildFragment.this.toString());
                } else if (!recyclerView.canScrollVertically(1)) {
                    refreshLayout.setEnabled(true);
                    LogUtils.e("滑动到了顶部--" + RankChildFragment.this.toString());
//                    zlass_index = zlass_index + 1;
//                    if(zlass == null || zlass_index == zlass.length){
//                        zlass_index = zlass_index -1;
                    if (zlass != null) {
                        if (!_isShowEnd) {
//                            items.add(new EndBean("我是有底线的"));
                            adapter.notifyDataSetChanged();
                            _isShowEnd = true;
                        } else {
//                            ToastUtils.showShort("已没有更多的数据");
                        }
                    }

//                        return;
//                    }
//                    String title = zlass[zlass_index];
//                    CardBean cardBean = new CardBean(title,null);
//                    items.add(cardBean);
                    //adapter.notifyDataSetChanged();
//                    getVodListData(cardBean,true);
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


    private Disposable disposable3;
    private Disposable disposable4;

    private void getCardData(int type, String day) {
        VodService vodService = Retrofit2Utils.INSTANCE.createByGson(VodService.class);
        if (AgainstCheatUtil.showWarn(vodService)) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            return;
        }
        vodService.getRankList(day, type + "", mIndex + "", "20")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<BaseResult<RankBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable3 != null && !disposable3.isDisposed()) {
                            disposable3.dispose();
                            disposable3 = null;
                        }
                        disposable3 = d;
                    }

                    @Override
                    public void onNext(BaseResult<RankBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                if (mIndex == 1) {
                                    list.clear();
                                }
                                list.addAll(result.getData().getList());
                                adapter.setItems(list);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (refreshLayout != null) refreshLayout.finishRefresh();
                        if (refreshLayout != null) refreshLayout.finishLoadMore();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        if (disposable3 != null && !disposable3.isDisposed()) {
            disposable3.dispose();
            disposable3 = null;
        }
        if (disposable4 != null && !disposable4.isDisposed()) {
            disposable4.dispose();
            disposable4 = null;
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(RankOrderEvent event) {
        mIndex = 1;
        DAY = getDay(event.rankOrder);
        Bundle args = getArguments();
        getCardData(args.getInt(key_position), DAY);
    }

    private String getDay(int day) {
        switch (day) {
            case 2:
                return "vod_hits_month desc";
            case 3:
                return "vod_hits_week desc";
            case 4:
                return "vod_hits_day desc";
            default:
                return "vod_hits desc";
        }
    }

}
