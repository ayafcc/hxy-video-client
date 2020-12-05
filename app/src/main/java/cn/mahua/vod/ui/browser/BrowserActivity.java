package cn.mahua.vod.ui.browser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.BarUtils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.mahua.vod.R;
import cn.mahua.vod.utils.UserUtils;
import okhttp3.Cookie;

public class BrowserActivity extends AppCompatActivity {
    WebView WV_browser;
    ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        BarUtils.setStatusBarLightMode(this, true);
        WV_browser = (WebView) findViewById(R.id.wv_browser);
        pb = (ProgressBar) findViewById(R.id.pb);
        ImageView backIv = (ImageView) findViewById(R.id.iv_task_back);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        List<Cookie> cookies = UserUtils.getLocalCookie();
        for(Cookie cookie : cookies){
            cookieManager.setCookie(url,cookie.name()+"="+cookie.value());
        }
        cookieManager.flush();
        cookieSyncMngr.sync();

        WV_browser.getSettings().setJavaScriptEnabled(true);
        WebSettings mWebSettings = WV_browser.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        WV_browser.canGoBack();



        WV_browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (pb != null)
                    pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (pb != null)
                    pb.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("mqqwpa://") || url.startsWith("mqqapi://")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                        return true;
                    } catch (Exception e) {
                    }
                }
                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                        return true;
                    } catch (Exception e) {
                    }
                }
                // ------- 处理结束 -------

                if (!(url.startsWith("http") || url.startsWith("https"))) return true;
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });

        WV_browser.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
