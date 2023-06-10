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
public class Downjiexi {
    private static final String TAG = "doJieXi--";
    private Handler handler = new Handler();
    private Context mContext;
    static Handler mainHanlder = new Handler(Looper.getMainLooper());
    private boolean mIsEnd = false;
}
