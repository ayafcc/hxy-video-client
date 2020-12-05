package cn.mahua.vod.ui.specialtopic;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.bean.BaseResult;
import cn.mahua.vod.bean.CardBean;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.TopicDetailBean;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.card.CardItemViewBinder;
import cn.mahua.vod.netservice.CardService;
import cn.mahua.vod.netservice.TopicDetailService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.ui.home.Vod;
import cn.mahua.vod.ui.play.PlayActivity;
import cn.mahua.vod.utils.Retrofit2Utils;
import cn.mahua.vod.utils.SimpleUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;
import cn.mahua.vod.utils.AgainstCheatUtil;
public class TopicDetailActivity extends BaseActivity {

    @BindView(R.id.back_item)
    LinearLayout backItem;

    @BindView(R.id.iv_topic_banner)
    ImageView topicBanner;

    @BindView(R.id.topic_name)
    TextView tvTopicName;

    @BindView(R.id.topic_info)
    TextView tvTopicInfo;

    @BindView(R.id.topic_videolist_view )
    RecyclerView topicVideoListView;

    private LinearLayoutManager layoutManager;
    private MultiTypeAdapter adapter;
    private List<Object> items = null;

    Disposable disposable1;
    Context context;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_topicdetail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        String topicId = getIntent().getStringExtra("topicid");

        initLoadView(topicId);
        initAdpterData();
       // getCardData();
        getTopicDetailData(topicId);
    }

    boolean isShowFirstItem;
    boolean _isShowEnd;

    void initLoadView(String topicId) {
        adapter = new MultiTypeAdapter();
        adapter.register(CardBean.class, new CardItemViewBinder(false,true).setActionListener(new CardItemViewBinder.CardItemActionListener() {
            @Override
            public void onClickMore(View view, Object name, List<VodBean> list,String title) {
            }

            @Override
            public void onClickChange(View view, Object o) {
            }

            @Override
            public void onClickItem(View view, Object item) {
                if (item instanceof Vod) {
                    Vod vod = (Vod) item;
                    PlayActivity.startByVod(vod);
                }
            }
        }));
//        adapter.register(EndBean.class, new EndItemViewBinder());
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
         topicVideoListView.setLayoutManager(layoutManager);
        //滑动加载
         topicVideoListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (layoutManager.findFirstVisibleItemPosition() != 0) {//离开里第一个ItemView
//                    if (!isShowFirstItem) {
////                        EventBus.getDefault().post(new TopBarEvent().setTopBarBg(null));
//                        isShowFirstItem = true;
//                    }
//                } else {
//                    isShowFirstItem = false;
//                }
                if (!recyclerView.canScrollVertically(-1)) {
                } else if (!recyclerView.canScrollVertically(1)) {
//                    if (!_isShowEnd) {
//                        items.add(new EndBean("我是有底线的"));
//                        adapter.notifyDataSetChanged();
//                        _isShowEnd = true;
//                    } else {
                        ToastUtils.showShort("已没有更多的数据");
//                    }
                }
            }
        });
         topicVideoListView.setAdapter(adapter);
    }

     void initAdpterData() {
        if (items != null) {
            items.clear();
            adapter.notifyDataSetChanged();
        } else {
            if (adapter==null){
                adapter = new MultiTypeAdapter();
            }
            adapter.setItems(items = new ArrayList<>());
            adapter.notifyDataSetChanged();
        }
        //初始化top的数据
//        topBean = null;
//        top_page = 1;
    }

    private void getCardData() {
        CardService cardService = Retrofit2Utils.INSTANCE.createByGson(CardService.class);
        if (AgainstCheatUtil.showWarn(cardService)) {
            return;
        }
        cardService.getCardList(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<CardBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable1 != null && !disposable1.isDisposed()) {
                            disposable1.dispose();
                            disposable1 = null;
                        }
                        disposable1 = d;
                    }

                    @Override
                    public void onNext(PageResult<CardBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                List<CardBean> list = result.getData().getList();
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
                      //  if (refreshLayout != null) refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void getTopicDetailData(String topicId) {
        TopicDetailService cardService = Retrofit2Utils.INSTANCE.createByGson(TopicDetailService.class);
        if (AgainstCheatUtil.showWarn(cardService)) {
            return;
        }
        cardService.getTopicDetail(topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<BaseResult<TopicDetailBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable1 != null && !disposable1.isDisposed()) {
                            disposable1.dispose();
                            disposable1 = null;
                        }
                        disposable1 = d;
                    }

                    @Override
                    public void onNext(BaseResult<TopicDetailBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {

                                TopicDetailBean topicDetailBean = result.getData();
                                SimpleUtils.setImageLoading(context,topicBanner, topicDetailBean.getTopic_pic(), R.drawable.topica);

                                tvTopicName.setText(topicDetailBean.getTopic_name());
                                tvTopicInfo.setText(topicDetailBean.getTopic_content().replace("<p>", "").replace("</p>", ""));

                                List<CardBean> list =  new ArrayList<>();
                                CardBean cardBean = new CardBean();
                                cardBean.setTitle("相关专题");
                                cardBean.setVods(topicDetailBean.getVods());


                                list.add(cardBean);
                                Log.i("xxa", new Gson().toJson(list).toString());
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
                        //  if (refreshLayout != null) refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void initCardData(List<CardBean> list) {
        if (list == null || list.size() <= 0) return;
        items.addAll(list);
        adapter.notifyDataSetChanged();
    }



    @OnClick(R.id.back_item)
    public void onViewClicked() {
        finish();
    }
}
