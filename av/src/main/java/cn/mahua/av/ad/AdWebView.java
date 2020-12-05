package cn.mahua.av.ad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.LogUtils;
public class AdWebView extends WebView {

    //替换img属性
    private static final String imgJs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='100%'}}\n</script>";
    //点击查看
    private static final String jsimg = "function()\n { var imgs = document.getElementsByTagName(\"img\");for(var i = 0; i < imgs.length; i++){  imgs[i].onclick = function()\n{AdJavascriptInterface.onClickImg(this.src);}}}";


    public AdWebView(Context context) {
        this(context, null);
    }

    public AdWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSetting();
    }

    private void initSetting() {
        addJavascriptInterface(new AdJavascriptInterface(), "AdJavascriptInterface");
        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                webView.loadUrl("javascript:(" + jsimg + ")()");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e(url);
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
                return true;
            }

        });

        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
    }

    public void loadHtmlBody(String html) {
        String data = AdBaseHtml.getHtml(html);
        data = imgJs + data;
        loadData(data, "text/html", "UTF-8");
    }


    /**
     *
     */
    public void onDestroy() {
        removeJavascriptInterface("AdJavascriptInterface");
        setWebViewClient(new WebViewClient());
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(false);
        loadData("", "text/html", "UTF-8");
    }
}
