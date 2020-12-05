package cn.mahua.vod.ui.screen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.mahua.vod.App;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.base.BaseItemClickListener;
import cn.mahua.vod.base.BaseItemClickListener2;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.TypeBean;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.netservice.ScreenService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.ui.filtrate.FiltrateItemViewBinder;
import cn.mahua.vod.ui.filtrate.FiltrateResult;
import cn.mahua.vod.ui.home.Vod;
import cn.mahua.vod.ui.play.PlayActivity;
import cn.mahua.vod.ui.seek.SeekActivity;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.DefaultItemAnimator;
import cn.mahua.vod.utils.Retrofit2Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;
public class ScreenActivity2 extends BaseActivity {

    private static final String KEY_TYPE = "KEY_TYPE";
    private static final String KEY_CLASS = "KEY_CLASS";
    private String mClass;
    private TypeBean mType;


    @BindView(R.id.rv_screen_result)
    RecyclerView rv_screen_result;
    private MultiTypeAdapter result_adapter;
    private List<Object> itmes;
    private Titles titles;
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
        Intent intent = new Intent(App.getInstance().getContext(), ScreenActivity2.class);
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

    private void initResult() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ((SimpleItemAnimator) rv_screen_result.getItemAnimator()).setSupportsChangeAnimations(false);
        rv_screen_result.setItemAnimator(new DefaultItemAnimator());
        //根据数据类型显示占据的item个数
        rv_screen_result.setNestedScrollingEnabled(false);
        rv_screen_result.setLayoutManager(linearLayoutManager);
        result_adapter = new MultiTypeAdapter();
        result_adapter.register(Titles.class, new ScreenClassItemViewBinder(new BaseItemClickListener2() {
            @Override
            public void onClickItem(View view, Object item,int postion) {
                if (filtrateResult.getList() != null) {
                    filtrateResult.getList().clear();
                }
                result_adapter.notifyDataSetChanged();
                mPage = 1;
                getData();
            }
        }));

        FiltrateItemViewBinder filtrateItemViewBinder = new FiltrateItemViewBinder();
        filtrateItemViewBinder.setBaseItemClickListener(new BaseItemClickListener() {
            @Override
            public void onClickItem(View view, Object item) {
                if (item instanceof Vod) {
                    Vod vod = (Vod) item;
                    PlayActivity.startByVod(vod);
                }
            }
        });
        result_adapter.register(FiltrateResult.class, filtrateItemViewBinder);

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

//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
//                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
//                    EventBus.getDefault().postSticky(new UpdateEvent(false));
//                    result_adapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
//                } else {
//                    EventBus.getDefault().postSticky(new UpdateEvent(true));
//                }
//                super.onScrollStateChanged(recyclerView, newState);
//            }
        });

        rv_screen_result.setAdapter(result_adapter);


        result_adapter.setItems(itmes = new ArrayList<>());

        titles = new Titles();
        String[] zlasss = mType.getType_extend().getZlass().split(",");
        List<Title> list = new ArrayList<>();
        list.add(new Title("全部", ""));
        for (String s : zlasss) {
            list.add(new Title(s, s));
        }
        titles.setTitles(list);
        titles.setTitle(mClass);
        if (titles.getCurTitle() == null) {
            titles.setCurTitle(titles.getTitles().get(0));
        }

        titles1 = new Titles();
        String[] lang = mType.getType_extend().getLang().split(",");
        List<Title> list1 = new ArrayList<>();
        list1.add(new Title("全部", ""));
        for (String s : lang) {
            list1.add(new Title(s, s));
        }
        titles1.setTitles(list1);
        titles1.setCurTitle(titles1.getTitles().get(0));

        titles2 = new Titles();
        String[] area = mType.getType_extend().getArea().split(",");
        List<Title> list2 = new ArrayList<>();
        list2.add(new Title("全部", ""));
        for (String s : area) {
            list2.add(new Title(s, s));
        }
        titles2.setTitles(list2);
        titles2.setCurTitle(titles2.getTitles().get(0));

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
//        titles4.setCurTitle(titles4.getTitles().get(0));

        itmes.add(titles2);
        itmes.add(titles);
//        itmes.add(titles1);
        itmes.add(titles3);
        itmes.add(titles4);
    }

    private void setResultData(List<VodBean> list) {
        if (list != null) {
            if (filtrateResult == null) {
                filtrateResult = new FiltrateResult();
                filtrateResult.setList(new ArrayList<>());
                itmes.add(filtrateResult);
            }
            filtrateResult.getList().addAll(list);
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
        screenService.getVodList(mType.getType_id(), titles.getCurTitle().getValue(), "", titles2.getCurTitle().getValue(), titles3.getCurTitle().getValue(), title4, mPage, 9)
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
}
