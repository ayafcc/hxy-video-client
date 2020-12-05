package cn.mahua.vod.ui.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseMainFragment;
import cn.mahua.vod.bean.GameBean;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.netservice.GameService;
import cn.mahua.vod.network.RetryWhen;
import cn.mahua.vod.utils.Retrofit2Utils;
import cn.mahua.vod.utils.SimpleUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.mahua.vod.utils.AgainstCheatUtil;
public class GameFragment extends BaseMainFragment {
    @BindView(R.id.game_listview)
    ListView gameListView;

    @BindView(R.id.banner_img)
    ImageView bannerImg;

    @BindView(R.id.tv_notice_gdmsg)
    TextView tvGdmsg;

    List<GameBean> gameBeans = new ArrayList<>();

    GameAdpter gameAdpter;

    ArrayList bobaomsgs = new ArrayList<>();

    Disposable disposable1;
    String bannerUrl="";

    boolean isInit;
    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_game;
    }

    public static GameFragment newInstance() {
        Bundle args = new Bundle();
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;
            gameBeans = new ArrayList<GameBean>();
//        for (int i=0; i<10; i++){
//            GameBean specialtTopicBean = new GameBean();
//            gameBeans.add(specialtTopicBean);
//        }
            gameAdpter = new GameAdpter(getActivity(), gameBeans);
            gameListView.setAdapter(gameAdpter);
            gameAdpter.notifyDataSetChanged();
            gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //H5
//                Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                intent.putExtra("url", gameBeans.get(position).getUrl());
//                intent.putExtra("parameter", "活动");
//                getActivity().startActivity(intent);

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gameBeans.get(position).getUrl()));
                    ActivityUtils.startActivity(intent);


                }
            });

            bannerImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bannerUrl != null && !bannerUrl.isEmpty()) {
//                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                    intent.putExtra("url", gameBeans.get(0).getUrl());
//                    intent.putExtra("parameter", "活动");
//                    getActivity().startActivity(intent);

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bannerUrl));
                        ActivityUtils.startActivity(intent);
                    }
                }
            });
            getGameData();

        }

    }

    private void getGameData() {
        GameService cardService = Retrofit2Utils.INSTANCE.createByGson(GameService.class);
        if (AgainstCheatUtil.showWarn(cardService)) {
            return;
        }
        cardService.getGameList("5", "1", "6")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .retryWhen(new RetryWhen(3, 3))
                .subscribe(new Observer<PageResult<GameBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable1 != null && !disposable1.isDisposed()) {
                            disposable1.dispose();
                            disposable1 = null;
                        }
                        disposable1 = d;
                    }

                    @Override
                    public void onNext(PageResult<GameBean> result) {
                        if (result != null) {
                            if (result.isSuccessful()) {
                                List<GameBean> list = result.getData().getList();
                                if(list!=null&&list.size()>0){
                                    SimpleUtils.setImageLoading(getContext(),bannerImg, list.get(0).getImg(), R.drawable.topica);
                                    tvGdmsg.setText(list.get(0).getName());
                                    bannerUrl = list.get(0).getUrl();
                                }
                                gameBeans.clear();
                                for (int i=1; i<list.size(); i++){
                                    GameBean gameBean = list.get(i);
                                    gameBeans.add(gameBean);
                                }

                                Log.i("xxxx===", new Gson().toJson(list));
                                gameAdpter.notifyDataSetChanged();
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
}
