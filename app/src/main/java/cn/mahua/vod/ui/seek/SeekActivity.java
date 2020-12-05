package cn.mahua.vod.ui.seek;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.mahua.vod.App;
import cn.mahua.vod.R;
import cn.mahua.vod.ad.AdWebView;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.StartBean;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.netservice.VodService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.ui.play.PlayActivity;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.Retrofit2Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;

public class SeekActivity extends BaseActivity {

    @BindView(R.id.awvSeek)
    AdWebView awvSeek;

    @BindView(R.id.shv_seek)
    SeekHistoryView seekHistoryView;

    @BindView(R.id.tv_seek_seek)
    TextView tv_seek;

    @BindView(R.id.et_seek_seek)
    EditText editText;

    @BindView(R.id.nsv)
    NestedScrollView nsv;


    @BindView(R.id.rv_seek_result)
    RecyclerView result_recyclerView;
    @BindView(R.id.tv_seek_hot_title)
    AppCompatTextView tvSeekHotTitle;
    @BindView(R.id.iv_seek_clear_seek)
    ImageView ivSeekClearSeek;
    @BindView(R.id.rvAssociate)
    RecyclerView rvAssociate;

    private MultiTypeAdapter result_adapter;
    private List<Object> result_items;

    @BindView(R.id.rv_seek_hot)
    RecyclerView recyclerView;
    private MultiTypeAdapter adapter;
    private List<Object> items;


    private boolean isSeek = false;

    private int mPage = 1;
    private String mWd;
    private boolean isEnd = false;
    private Disposable disposable;
    private Disposable disposable2;
    private AssociateAdapter associateAdapter;
    private boolean isAssociate;//是否是联想结果点击改变的搜索框的文字

    @OnClick(R.id.tv_seek_seek)
    void seek() {
        if (isSeek) {
            tv_seek.setText("搜索");
            nsv.setVisibility(View.VISIBLE);
            result_recyclerView.setVisibility(View.GONE);
            if (result_items != null) {
                result_items.clear();
            }
            result_adapter.notifyDataSetChanged();
            isSeek = false;
        } else {
            tv_seek.setText("取消");
            nsv.setVisibility(View.GONE);
            result_recyclerView.setVisibility(View.VISIBLE);
            isSeek = true;
            String data = editText.getText().toString();
            if (!StringUtils.isEmpty(data)) {
                seekHistoryView.addData(data);
            }
            mWd = editText.getText().toString();
            mPage = 1;
            isEnd = false;
            KeyboardUtils.hideSoftInput(this);
            getResultData(mWd);
        }
    }

    @OnClick(R.id.iv_seek_clear_seek)
    void clearSeek() {
        editText.setText("");
    }

    @OnClick(R.id.iv_seek_clear_history)
    void clearHistory() {
        if (seekHistoryView != null) {
            seekHistoryView.clear();
        }
    }

    @OnClick(R.id.rlBack)
    void back() {
        finish();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_seek;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /// BarUtils.setStatusBarColor(this, getResources().getColor(R.color.colorAccent));
        seekHistoryView.setOnItemClickListener((view, data) -> {
            showSearchResult(data);
        });

        initAd();
        initHotView();
        initHot(App.searchHot);

        initResultView();
        initAssociateResult();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isAssociate) {
                    isAssociate = false;
                    return;
                }
                if (TextUtils.isEmpty(s.toString())) {
                    ivSeekClearSeek.setVisibility(View.GONE);
                    hideAsociateView();
                } else {
                    ivSeekClearSeek.setVisibility(View.VISIBLE);
                    getAssociateResult(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“搜索”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideSoftInput(SeekActivity.this);
                    tv_seek.setText("取消");
                    nsv.setVisibility(View.GONE);
                    result_recyclerView.setVisibility(View.VISIBLE);
                    isSeek = true;

                    String data = editText.getText().toString();
                    if (!StringUtils.isEmpty(data)) {
                        seekHistoryView.addData(data);
                    }
                    mWd = editText.getText().toString();
                    mPage = 1;
                    isEnd = false;
                    getResultData(mWd);
                    return true;
                }
                return false;
            }
        });
    }

    private void initAd() {
        if (App.startBean != null && App.startBean.getAds() != null && App.startBean.getAds().getSearcher() != null) {
            StartBean.Ad searcher = App.startBean.getAds().getSearcher();
            String description = searcher.getDescription();
            if (StringUtils.isEmpty(description) || searcher.getStatus() == 0) {
                awvSeek.setVisibility(View.GONE);
            } else {
                awvSeek.setVisibility(View.VISIBLE);
                awvSeek.loadHtmlBody(description);
            }
        } else {
            awvSeek.setVisibility(View.GONE);
        }
    }

    private void initHotView() {
        adapter = new MultiTypeAdapter();
        SeekHotItemViewBinder seekHotItemViewBinder = new SeekHotItemViewBinder();
        seekHotItemViewBinder.setBaseItemClickListener((view, item) -> {
            showSearchResult(item.toString());
        });
        adapter.register(String.class, seekHotItemViewBinder);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initHot(List<String> list) {
        if (list == null) {
            tvSeekHotTitle.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        if (items == null) {
            items = new ArrayList<>();
            adapter.setItems(items);
        }
        if (list.size() > 20) {
            list = list.subList(0, 20);
        }
        if (items != null) {
            items.clear();
            items.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    private void initResultView() {
        result_adapter = new MultiTypeAdapter();
        SeekResultItemViewBinder seekResultItemViewBinder = new SeekResultItemViewBinder();
        seekResultItemViewBinder.setBaseItemClickListener((view, item) -> {
            if (item instanceof VodBean) {
                VodBean vodBean = (VodBean) item;
                PlayActivity.startByVod(vodBean);
            }
        });
        result_adapter.register(VodBean.class, seekResultItemViewBinder);
//        result_adapter.register(EndBean.class, new EndItemViewBinder());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        result_recyclerView.setLayoutManager(linearLayoutManager);

        //滑动加载
        result_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    LogUtils.e("滑动到了顶部--");
                } else if (!recyclerView.canScrollVertically(1)) {
                    LogUtils.e("滑动到了底部--");
                    if (isEnd) return;
                    mPage = mPage + 1;
                    getResultData(mWd);
                }
            }
        });
        result_recyclerView.setAdapter(result_adapter);
    }

    private void initAssociateResult() {
        rvAssociate.setLayoutManager(new LinearLayoutManager(this));
        associateAdapter = new AssociateAdapter();
        associateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VodBean item = (VodBean) adapter.getItem(position);
                hideAsociateView();
                showSearchResult(item.getVodName());
            }
        });
        rvAssociate.setAdapter(associateAdapter);
    }

    private void showSearchResult(String data) {
        tv_seek.setText("取消");
        nsv.setVisibility(View.GONE);
        result_recyclerView.setVisibility(View.VISIBLE);
        isSeek = true;
        isAssociate = true;
        mWd = data;
        editText.setText(data);
        editText.setSelection(data.length());
        if (!StringUtils.isEmpty(data)) {
            seekHistoryView.addData(data);
        }
        KeyboardUtils.hideSoftInput(this);
        mPage = 1;
        isEnd = false;
        getResultData(data);
    }

    private void getAssociateResult(String data) {
        VodService vodService = Retrofit2Utils.INSTANCE.createByGson(VodService.class);
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        vodService.getVodList(1, 20, data)
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
                                if (list != null && list.size() != 0) {
                                    showAssociateView();
                                    associateAdapter.setNewData(list);
                                } else {
                                    hideAsociateView();
                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void initResult(List<?> list) {
        if (list == null) return;
        if (result_items == null) {
            result_items = new ArrayList<>();
            result_adapter.setItems(result_items);
        } else {
            if (mPage == 1 && result_items != null) {
                result_items.clear();
            }
        }
        result_items.addAll(list);
        result_adapter.notifyDataSetChanged();
    }

    private void getResultData(String data) {
        VodService vodService = Retrofit2Utils.INSTANCE.createByGson(VodService.class);
        if (AgainstCheatUtil.showWarn(vodService)) {
            return;
        }
        vodService.getVodList(mPage, 10, data)
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
                                List<VodBean> list = result.getData().getList();
                                hideAsociateView();
                                if (list == null || list.size() == 0) {
                                    if (mPage == 1) {
                                        result_items = new ArrayList<>();
//                                        result_items.add(new EndBean(" 未搜索到结果，联系客服添加 "));
                                        result_adapter.setItems(result_items);
                                        result_adapter.notifyDataSetChanged();
                                    } else {
//                                        result_items.add(new EndBean("我是有底线的"));
                                        result_adapter.notifyDataSetChanged();
                                    }
                                    isEnd = true;
                                } else {
                                    initResult(list);
                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void showAssociateView() {
        rvAssociate.setVisibility(View.VISIBLE);
    }

    private void hideAsociateView() {
        rvAssociate.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressedSupport() {
        if (isSeek) {
            seek();
        } else {
            super.onBackPressedSupport();
        }
    }
}
