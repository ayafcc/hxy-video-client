package cn.mahua.vod.ui.game;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.h5_webview)
    WebView h5Webview;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_web_layout;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        h5Webview = (WebView) this.findViewById(R.id.h5_webview);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("parameter");
        tvTitle.setText(title);

        WebSettings webSettings = h5Webview.getSettings();
        webSettings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true);//支持js
//        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
//        webSettings.setSupportZoom(true); // 可以缩放
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 得到 URL 可以传给应用中的某个 WebView 页面加载显示
//                return true;
//            }
//        });

        h5Webview.setWebChromeClient(new WebChromeClient());
        h5Webview.setWebViewClient(new WebViewClient());
        h5Webview.getSettings().setJavaScriptEnabled(true);


//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);

        if (url != null && url.length() > 0) {
            h5Webview.loadUrl(url);
        } else {
            h5Webview.loadDataWithBaseURL(null, "<div>没有内容<div>", "text/html", "UTF-8", null);
        }
    }

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        finish();
    }
}
