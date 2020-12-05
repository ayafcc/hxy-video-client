package cn.mahua.vod.ui.play;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;

import cn.mahua.vod.R;
import cn.mahua.vod.base.BaseActivity;
import cn.mahua.vod.ui.widget.X5WebView;

/**
 * @author kong
 * @description:
 */
public class X5WebViewActivity extends BaseActivity {
    private X5WebView webView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        webView = findViewById(R.id.myweb);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        webView.setDrawingCacheEnabled(true);
        webView.loadUrl(url);
        enablePageVideoFunc();//窗口创建完毕开启页面内全屏
        //隐藏滚动条
        if (webView.getX5WebViewExtension() != null) {
            webView.getX5WebViewExtension().setHorizontalScrollBarEnabled(false);//水平不显示滚动按钮
            webView.getX5WebViewExtension().setVerticalScrollBarEnabled(false); //垂直不显示滚动按钮
        }
        //添加javascript事件
        webView.addJavascriptInterface(new WebViewJavaScriptFunction() {
            @Override
            public void onJsFunctionCalled(String tag) {

            }

            @JavascriptInterface
            public void onX5ButtonClicked() {
                X5WebViewActivity.this.enableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onCustomButtonClicked() {
                X5WebViewActivity.this.disableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onLiteWndButtonClicked() {
                X5WebViewActivity.this.enableLiteWndFunc();
            }

            @JavascriptInterface
            public void onPageVideoClicked() {
                X5WebViewActivity.this.enablePageVideoFunc();
            }

        }, "Android");

    }

    //判断内核是否加载失败这个方法我没调用
    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) {
            Toast.makeText(this, "x5加载失败", Toast.LENGTH_LONG).show();
            QbSdk.initX5Environment(this, null);
        }
        Toast.makeText(this, "x5加载OK", Toast.LENGTH_LONG).show();
    }

    private void enableX5FullscreenFunc() {

        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        } else {
            Toast.makeText(this, "开启X5全屏播放模式失败view为空", Toast.LENGTH_LONG).show();
        }
    }

    private void disableX5FullscreenFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enableLiteWndFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enablePageVideoFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_play_video;
    }
}