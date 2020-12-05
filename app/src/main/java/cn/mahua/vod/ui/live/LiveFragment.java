package cn.mahua.vod.ui.live;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.mahua.vod.App;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseMainFragment;
import cn.mahua.vod.bean.LiveBean;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.TitleEvent;
import cn.mahua.vod.netservice.LiveService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.DensityUtils;
import cn.mahua.vod.utils.Retrofit2Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LiveFragment extends BaseMainFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView titleTv;
    List<LiveBean> liveBeans;
    LiveAdpter liveAdpter;
    Disposable disposable1;

    boolean isInit;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_live;
    }

    public static LiveFragment newInstance() {
        Bundle args = new Bundle();
        LiveFragment fragment = new LiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        if (HawkHelper.getInstance().getLiveDate() != null) {
//            liveBeans = HawkHelper.getInstance().getLiveDate().list;
//        }

        if (liveBeans == null) {
            liveBeans = new ArrayList<LiveBean>();
        }

        liveAdpter = new LiveAdpter(getActivity(), liveBeans);
        recyclerView.setLayoutManager(new GridLayoutManager(App.getApplication(),2));
        recyclerView.setAdapter(liveAdpter);
        liveAdpter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int padding_half = DensityUtils.INSTANCE.dp2px(App.getApplication(), 2);
                int padding = DensityUtils.INSTANCE.dp2px(App.getApplication(), 4);
                switch (position % 2) {
                    case 0:
                        outRect.set(padding, 0, padding_half, 0);
                        break;
                    case 1:
                        outRect.set(padding_half, 0, padding, 0);
                        break;
                }
            }
        });
        getLiveData();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;

        }
    }


    private void getLiveData() {
        LiveService liveService = Retrofit2Utils.INSTANCE.createByGson(LiveService.class);
        if (AgainstCheatUtil.showWarn(liveService)) {
            return;
        }

        liveService.getLiveList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<LiveBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable1 != null && !disposable1.isDisposed()) {
                            disposable1.dispose();
                            disposable1 = null;
                        }
                        disposable1 = d;
                    }

                    @Override
                    public void onNext(PageResult<LiveBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                List<LiveBean> list = result.getData().getList();

                                if (liveBeans != null) {
                                    liveBeans.clear();
                                } else {
                                    liveBeans = new ArrayList<>();
                                }


                                liveBeans.addAll(list);
                                Log.i("xxxx===", new Gson().toJson(list));
                                liveAdpter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
//                        if (refreshLayout != null) refreshLayout.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onDestroyView() {
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
            disposable1 = null;
        }

        super.onDestroyView();
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(TitleEvent event) {
        titleTv.setText(event.title);
    }


}
