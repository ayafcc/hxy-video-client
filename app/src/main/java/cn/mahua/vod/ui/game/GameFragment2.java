package cn.mahua.vod.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseMainFragment;
import cn.mahua.vod.bean.GetUrlEvent;

public class GameFragment2 extends BaseMainFragment {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.tv_title)
    TextView titleTv;
    String url;
    String title;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_game2;
    }

    public static GameFragment2 newInstance() {
        Bundle args = new Bundle();
        GameFragment2 fragment = new GameFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && webview != null) {
            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl(url);
            titleTv.setText(title);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(GetUrlEvent event) {
        url = event.url;
        title = event.title;
    }

}
