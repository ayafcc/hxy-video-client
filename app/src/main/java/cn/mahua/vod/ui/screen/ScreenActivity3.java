package cn.mahua.vod.ui.screen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.mahua.vod.App;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.RefreshEvent;
import cn.mahua.vod.bean.TypeBean;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.netservice.ScreenService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.ui.filtrate.FiltrateResult;
import cn.mahua.vod.ui.home.MyDividerItemDecoration;
import cn.mahua.vod.ui.seek.SeekActivity;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.DefaultItemAnimator;
import cn.mahua.vod.utils.Retrofit2Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static net.lucode.hackware.magicindicator.ScrollState.SCROLL_STATE_IDLE;
import static org.litepal.LitePalApplication.getContext;

public class ScreenActivity3 extends BaseActivity {

    private static final String KEY_TYPE = "KEY_TYPE";
    private static final String KEY_CLASS = "KEY_CLASS";
    private String mClass;
    private TypeBean mType;


    @BindView(R.id.rv_screen_result)
    RecyclerView rv_screen_result;
    private MainRecyclerViewAdapter result_adapter;
    private List<VodBean> itmes = new ArrayList<>();
    private Titles titles1;
    private Titles titles2;
    private Titles titles3;
    private Titles titles4;
    private FiltrateResult filtrateResult;
    @BindView(R.id.srl_home_other_child)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;

    @OnClick(R.id.rlBack)
    void back() {
        finish();
    }

    @OnClick(R.id.iv_screen_seek)
    void seek() {
        ActivityUtils.startActivity(SeekActivity.class);
    }


    public static void start(String zlass, TypeBean type) {
        Intent intent = new Intent(App.getInstance().getContext(), ScreenActivity3.class);
        intent.putExtra(KEY_TYPE, type);
        intent.putExtra(KEY_CLASS, zlass);
        ActivityUtils.startActivity(intent, R.anim.slide_in_right, R.anim.no_anim);
    }

    @BindView(R.id.tv_screen_title)
    TextView tv_screen_title;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_screen2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        Intent intent = getIntent();
        if (intent != null) {
            mClass = intent.getStringExtra(KEY_CLASS);
            mType = (TypeBean) intent.getSerializableExtra(KEY_TYPE);
            if (mType == null) {
                return;
            }
            tv_screen_title.setText(mType.getTypeName());
        } else {
            return;
        }
        SwipeRefreshLayout.OnRefreshListener onRefreshListener;
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage = mPage + 1;
                getData();
            }
        });
        initResult();
        getData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initResult() {
        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(getContext(), RecyclerView.HORIZONTAL, false);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.divider_image));
        rv_screen_result.addItemDecoration(dividerItemDecoration);
        ((SimpleItemAnimator) rv_screen_result.getItemAnimator()).setSupportsChangeAnimations(false);
        rv_screen_result.setItemAnimator(new DefaultItemAnimator());
//        //根据数据类型显示占据的item个数
        rv_screen_result.setNestedScrollingEnabled(false);
        rv_screen_result.setLayoutManager(new MainRecyclerViewAdapter.GridLayoutManager(getContext()));
        result_adapter = new MainRecyclerViewAdapter(this, rv_screen_result);
//        result_adapter.register(Titles.class, new ScreenClassItemViewBinder(new BaseItemClickListener() {
//            @Override
//            public void onClickItem(View view, Object item) {
//                if (filtrateResult.getList() != null) {
//                    filtrateResult.getList().clear();
//                }
//                result_adapter.notifyDataSetChanged();
//                mPage = 1;
//                getData();
//            }
//        }));


        //滑动加载
        rv_screen_result.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) ||
                        !recyclerView.canScrollVertically(-1)) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
                    result_adapter.setScrolling(false);
                    result_adapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
                } else
                    result_adapter.setScrolling(true);
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        titles2 = new Titles();
        String[] zlasss = mType.getType_extend().getZlass().split(",");
        List<Title> list = new ArrayList<>();
        list.add(new Title("全部", ""));
        for (String s : zlasss) {
            list.add(new Title(s, s));
        }
        titles2.setTitles(list);
        if (mClass.isEmpty()) {
            titles2.setCurTitle(titles2.getTitles().get(0));
        } else {
            if (titles2.getCurTitle() == null) {
                for (int i = 0; i < list.size(); i++) {
                    Title title = list.get(i);
                    if (title.getTitle().equals(mClass)) {
                        titles2.setCurTitle(titles2.getTitles().get(i));
                    }
                }
            }
        }

        titles1 = new Titles();
        String[] area = mType.getType_extend().getArea().split(",");
        List<Title> list2 = new ArrayList<>();
        list2.add(new Title("全部", ""));
        for (String s : area) {
            list2.add(new Title(s, s));
        }
        titles1.setTitles(list2);
        titles1.setCurTitle(titles1.getTitles().get(0));

        titles3 = new Titles();
        String[] years = mType.getType_extend().getYear().split(",");
        List<Title> list3 = new ArrayList<>();
        list3.add(new Title("全部", ""));
        for (String s : years) {
            list3.add(new Title(s, s));
        }
        titles3.setTitles(list3);
        titles3.setCurTitle(titles3.getTitles().get(1));

        titles4 = new Titles();

        List<Title> list4 = new ArrayList<>();
        list4.add(new Title("最多播放", "hits"));
        list4.add(new Title("最近更新", "time"));
        list4.add(new Title("最多收藏", "store_num"));
        list4.add(new Title("最高评分", "score"));
        titles4.setTitles(list4);

        result_adapter.setHeader(titles1, titles2, titles3, titles4);
        result_adapter.setDataList(itmes);
        rv_screen_result.setAdapter(result_adapter);
    }

    private void setResultData(List<VodBean> list) {
        if (list != null) {
            if (mPage == 1) {
                itmes.clear();
            }
            itmes.addAll(list);
            result_adapter.setDataList(itmes);
            result_adapter.notifyDataSetChanged();
        }
    }

    private ScreenService screenService;
    private Disposable disposable;

    private void getData() {
        if (screenService == null) {
            screenService = Retrofit2Utils.INSTANCE.createByGson(ScreenService.class);
        }
        if (AgainstCheatUtil.showWarn(screenService)) {
            refreshLayout.finishLoadMore();
            return;
        }
        String title4 = "";

        if (titles4.getCurTitle() != null) {
            title4 = titles4.getCurTitle().getValue();
        }
        screenService.getVodList(mType.getType_id(), titles2.getCurTitle().getValue(), "", titles1.getCurTitle().getValue(), titles3.getCurTitle().getValue(), title4, mPage, 9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<VodBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                            disposable = null;
                        }
                        disposable = d;
                    }

                    @Override
                    public void onNext(PageResult<VodBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                List<VodBean> list = result.getData().getList();
                                if (list == null || list.size() < 1) {
                                } else {
                                    setResultData(list);
                                }
                            }
                        }
//                        result_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        result_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        if (refreshLayout != null) refreshLayout.finishLoadMore(200);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(RefreshEvent event) {
//        result_adapter.setHeader(titles1, titles2, titles3, titles4);
//        result_adapter.notifyDataSetChanged();
        mPage = 1;
        getData();
    }
}
