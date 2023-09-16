package cn.mahua.vod.jiexi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.StormWyrm.wanandroid.base.exception.ResponseException;
import com.github.StormWyrm.wanandroid.base.net.RequestManager;
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import cn.mahua.vod.App;
import cn.mahua.vod.bean.AppConfigBean;
import cn.mahua.vod.bean.JieXiPlayBean;
import cn.mahua.vod.netservice.VodService;
import cn.mahua.vod.utils.AgainstCheatUtil;
import cn.mahua.vod.utils.OkHttpUtils;
import cn.mahua.vod.utils.Retrofit2Utils;
import okhttp3.Call;
import okhttp3.Response;

@SuppressLint("SetJavaScriptEnabled")
public class JieXiWebView2 extends WebView {
    private static final String TAG = "JieXi--";
    private int index = 0;
    private int size = 0;
    private BackListener mBackListener =new BackListener() {
        @Override
        public void onSuccess(String url, int curParseIndex,Map<String, String> headers) {

        }

        @Override
        public void onError() {

        }

        @Override
        public void onProgressUpdate(String msg) {

        }
    };
    private final Handler handler = new Handler();
    private final Context mContext;
    static final Handler mainHanlder = new Handler(Looper.getMainLooper());
    private boolean mIsEnd = false;
    private Map<String, String> headers = new Map<String, String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable Object o) {
            return false;
        }

        @Nullable
        @Override
        public String get(@Nullable Object o) {
            return null;
        }

        @Nullable
        @Override
        public String put(String s, String s2) {
            return null;
        }

        @Nullable
        @Override
        public String remove(@Nullable Object o) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends String> map) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<String> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<String, String>> entrySet() {
            return null;
        }
    };

    public JieXiWebView2(Context context, String parses, BackListener backListener) {
        super(context);
        mContext = context;
        if (parses == null || parses.isEmpty()) {
            System.out.println("----- parses 空空");
            backListener.onError();
        } else {
            mBackListener = backListener;
            initSetting();
        }

        if (timer == null) {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    if (mBackListener != null) {
                        mBackListener.onProgressUpdate("正在获取资源 " + (time2 - time + 1) + "s");
                    }
                    if (--time <= 0) {
                        time = time2;
                        if (timer!=null) {
                            timer.cancel();
                        }
                        task = null;
                        timer = null;
                        if (mBackListener != null) {
                            LogUtils.d("", "====Parse jiexi6 url=");
                            mBackListener.onSuccess("", index,null);
                        }
                    }
                }
            };
            timer.schedule(task, 0, 1000);
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        mBackListener = null;
    }

    private int time = 8;//一条线路探8s
    private int time2 = 8;
    private Timer timer;
    private TimerTask task;

    public void startParse(String realUrl, int curIndex, int curSize, boolean isEnd) throws NoSuchAlgorithmException {
        mIsEnd = isEnd;
        System.out.println("----- mIsEnd" + mIsEnd + "  curIndex=" + curIndex);
        if (isWifiProxy() || isVpnUsed()) {
            AgainstCheatUtil.showWarn(null);
            return;
        }
        if(realUrl.contains("zhenbuka.com")){
            time = 20;
            time2 = 20;
        }
        index = curIndex;
        size = curSize;
        if (realUrl.contains("json") || realUrl.contains("..") || realUrl.contains("...")|| realUrl.contains("&iktoken=ik2021")) {
            if(realUrl.contains("&iktoken=ik2021")){
                realUrl = realUrl.replaceFirst("&iktoken=ik2021", "");
                LogUtils.d("", "====Parse jiexi realUrl2="+realUrl);
                getJsonResult(realUrl, false);
            }
            if (realUrl.contains("...")) {
                realUrl = realUrl.replaceFirst("\\.\\.\\.", "\\.");
                LogUtils.d("", "====Parse jiexi realUrl="+realUrl);
                getJsonResult(realUrl, true);
            }
            if (realUrl.contains("..")) {
                realUrl = realUrl.replaceFirst("\\.\\.", "\\.");
                LogUtils.d("", "====Parse jiexi realUrl2="+realUrl);
                getJsonResult(realUrl, false);
            }
        } else {
            String finalRealUrl = realUrl;
            handler.post(() -> loadUrl(finalRealUrl));
        }
    }

    private void initSetting() {
        setClickable(true);
        setWebViewClient(webViewClient);
        WebSettings webSetting = getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webSetting.setSupportZoom(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setGeolocationEnabled(true);
        webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSetting.setSupportMultipleWindows(true);
    }


    private final WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            Log.i(TAG, "请求的url：" + url);
            if (url.contains(".mp4") || url.contains(".m3u8") || url.contains("/m3u8?") || url.contains(".flv") || url.contains("aliyuncs.com")) {
                if( url.indexOf("mi-img.com")==-1 ) {
                    if(url.indexOf("=http")!=-1 || url.indexOf("=https")!=-1 || url.indexOf("=https%3A%2F")!=-1 || url.indexOf("=http%3A%2F")!=-1 ) {
                        url = StringUrlseek.seekurl(url);
                    }
                    headers = request.getRequestHeaders();
                        //ToastUtils.showShort("webview2解析到播放地址："+ url);
                        Log.i(TAG, "webview解析到播放地址：" + url);
                    try {
                        String decode_url1 = URLDecoder.decode(url,"UTF-8");
                         url = decode_url1;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    try {
                        String decode_url1 = URLDecoder.decode(url,"UTF-8");
                        url = decode_url1;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if(url.contains("aliyuncs.com")){
                        if(url.contains(".oss")){
                            if (mBackListener != null) {
                                LogUtils.d("", "====Parse jiexi1 realUrl=" + url);
                                mBackListener.onSuccess(url, index,headers);
                                mBackListener = null;//获取到解析地址后就不回调了
                            }
                        }

                    }else{
                        if (mBackListener != null) {
                            LogUtils.d("", "====Parse jiexi1 realUrl=" + url);
                            mBackListener.onSuccess(url, index,headers);
                            mBackListener = null;//获取到解析地址后就不回调了
                        }
                    }



            }
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);
            handler.proceed();// 接受所有网站的证书
        }
    };


    /**
     * @param isOnlyUrl true 就是 直接返回 链接，不是 Data格式
     */
    private void getJsonResult(String url, boolean isOnlyUrl) throws NoSuchAlgorithmException {
        System.out.println("---play----  req" + url);
        OkHttpUtils.getInstance()
                .getDataAsynFromNet(url, new OkHttpUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException {
                        boolean isSuccessful = response.isSuccessful();
                        if (isSuccessful) {
                            try {
                                if (isOnlyUrl) {
                                    if (mBackListener != null) {
                                        LogUtils.d("", "====Parse jiexi2 url="+response.body().string());
                                        mBackListener.onSuccess(response.body().string(), index,null);
                                        mBackListener = null;//获取到解析地址后就不回调了
                                    }
                                } else {
                                    JieXiPlayBean playBean = GsonUtils.fromJson(response.body().string(), JieXiPlayBean.class);
                                    if (playBean.getCode().equals("200")) {
                                        Log.i(TAG, "json解析到播放地址：" + url + "playurl==>" + playBean.getUrl());
                                        mainHanlder.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (mBackListener != null) {
                                                    if(playBean.getUrl()==null){
                                                        if (mBackListener != null) {
                                                            mBackListener.onError();
                                                        }
                                                        return;
                                                    }
                                                    if (playBean.getUrl().contains(".m3u8") || playBean.getUrl().contains("/m3u8?") || playBean.getUrl().contains(".mp4") || playBean.getUrl().contains(".flv")|| playBean.getUrl().contains("aliyuncs.com")|| playBean.getUrl().contains("myhuaweicloud.com")) {
                                                        //if (playBean.getUrl().indexOf("=http") == -1 && playBean.getUrl().indexOf("=https") == -1 && playBean.getUrl().indexOf("=https%3A%2F") == -1 && playBean.getUrl().indexOf("=http%3A%2F") == -1) {
                                                            String playUrl = playBean.getUrl();
                                                            if (playUrl.contains("http") || playUrl.contains("https")) {
                                                                playBean.setUrl(playUrl);
                                                            }else if(playUrl.startsWith("//")){
                                                                playBean.setUrl("https:" + playUrl);
                                                            } else {
                                                                playBean.setUrl("https://" + playUrl);
                                                            }
                                                            Log.i(TAG, "json解析到播放地址22：" + url + "playurl==>" + playBean.getUrl());
                                                        //}
                                                    }
                                                    System.out.println("---play----  result" + playBean.getUrl());
                                                    if(playBean.getUrl().contains("aliyuncs.com")){
                                                        if(playBean.getUrl().contains(".oss")){
                                                            if (mBackListener != null) {
                                                                LogUtils.d("", "====Parse jiexi3 url=" + playBean.getUrl());
                                                                mBackListener.onSuccess(playBean.getUrl(), index, null);
                                                                mBackListener = null;//获取到解析地址后就不回调了
                                                            }
                                                        }
                                                    }else if(playBean.getUrl().contains("myhuaweicloud.com")){
                                                        if(playBean.getUrl().contains("/data/")){
                                                            if (mBackListener != null) {
                                                                LogUtils.d("", "====Parse jiexi3 url=" + playBean.getUrl());
                                                                mBackListener.onSuccess(playBean.getUrl(), index, null);
                                                                mBackListener = null;//获取到解析地址后就不回调了
                                                            }
                                                        }
                                                    }else {
                                                        if (mBackListener != null) {
                                                            LogUtils.d("", "====Parse jiexi3 url=" + playBean.getUrl());
                                                            mBackListener.onSuccess(playBean.getUrl(), index, null);
                                                            mBackListener = null;//获取到解析地址后就不回调了
                                                        }
                                                    }

                                                }
                                            }
                                        });
                                    } else {
                                        Log.i(TAG, "json返回值 解析不成功");
                                        if (mBackListener != null && mIsEnd) {
                                            mBackListener.onError();
                                        }
                                        if (mBackListener != null) {
                                            LogUtils.d("", "====Parse jiexi3 url=");
                                            mBackListener.onSuccess("", index,null);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.i(TAG, "json解析错误：" + e);
                                if (mBackListener != null && mIsEnd) {
                                    mBackListener.onError();
                                }
                                if (mBackListener != null) {
                                    LogUtils.d("", "====Parse jiexi4 url=");
                                    mBackListener.onSuccess("", index,null);
                                }
                            }
                        } else {
                            Log.i(TAG, "json解析错误：非200");
                            if (mBackListener != null && mIsEnd) {
                                mBackListener.onError();
                            }
                            if (mBackListener != null) {
                                LogUtils.d("", "====Parse jiexi5 url=");
                                mBackListener.onSuccess("", index,null);
                            }
                        }
                    }

                    @Override
                    public void failed(Call call, IOException e) {
                        Log.i(TAG, "json解析错误：" + e);
                    }
                });
    }

    private boolean isWifiProxy() {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(App.getInstance().getContext());
            proxyPort = android.net.Proxy.getPort(App.getInstance().getContext());
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
        //return false;
    }

    /**
     * 检测是否正在使用VPN，如果在使用返回true,反之返回flase
     */
    public static boolean isVpnUsed() {
        try {
            Enumeration niList = NetworkInterface.getNetworkInterfaces();
            if (niList != null) {
                for (Object f : Collections.list(niList)) {
                    NetworkInterface intf = (NetworkInterface) f;
                    if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    Log.d("-----", "isVpnUsed() NetworkInterface Name: " + intf.getName());
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                        return true; // The VPN is up
                        //return false;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

}
