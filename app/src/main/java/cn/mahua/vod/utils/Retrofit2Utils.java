package cn.mahua.vod.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.net.NetworkInterface;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.App;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@SuppressWarnings("unused")
public enum Retrofit2Utils {
    INSTANCE;

    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 5;

    private Retrofit mGsonRetrofit;
    private Retrofit mScalarsRetrofit;

    Retrofit2Utils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //请求配置
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接 超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作 超时时间
        builder.proxy(Proxy.NO_PROXY);
        //日志拦截，如需要自行继续添加
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                Log.i("Retrofit2Utils", s);
            }
        });
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        builder.cookieJar(new CookieJar() {

            @Override
            public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                String url = httpUrl.url().toString();
                if (url.contains(ApiConfig.BASE_URL + ApiConfig.LOGIN)) {
                    UserUtils.saveCookies(list);
                }
            }

            @NotNull
            @Override
            public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                List<Cookie> cookies = new ArrayList<>();
                String urlStr = httpUrl.url().getPath();

                for (String needLogin : UserUtils.getNeedLogin()) {
                    if (urlStr.equals(needLogin)) {
                        cookies.addAll(UserUtils.getLocalCookie());
                        break;
                    }
                }
                return cookies;
            }
        });
        builder.retryOnConnectionFailure(true);

        mGsonRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConfig.BASE_URL)
                .build();
        mScalarsRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(ApiConfig.BASE_URL)
                .build();
    }

    /**
     * 获取对应GsonConverterFactory的Service
     *
     * @param service Service 的 class
     * @return Service
     */
    public <T> T createByGson(Class<T> service) {
        if (isWifiProxy() || isVpnUsed()) {
            return null;
        }
        return INSTANCE.mGsonRetrofit.create(service);
    }

    /**
     * 获取对应GsonConverterFactory的Service
     *
     * @param service Service 的 class
     * @return Service
     */
    public <T> T createByGson(Class<T> service, String base_url) {
        if (isWifiProxy() || isVpnUsed()) {
            return null;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //请求配置
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接 超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作 超时时间
        builder.proxy(Proxy.NO_PROXY);
        //日志拦截，如需要自行继续添加
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);

        builder.retryOnConnectionFailure(true);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url)
                .build();
        return retrofit.create(service);
    }

    /**
     * 获取对应ScalarsConverterFactory的Service
     *
     * @param service Service 的 class
     * @return Service
     */
    public <T> T createByScalars(Class<T> service) {
        if (isWifiProxy() || isVpnUsed()) {
            return null;
        }
        return INSTANCE.mScalarsRetrofit.create(service);
    }

    public <T> T createByScalars(Class<T> service, String base_url) {
        if (isWifiProxy() || isVpnUsed()) {
            return null;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //请求配置
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接 超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作 超时时间
        builder.proxy(Proxy.NO_PROXY);

        //日志拦截，如需要自行继续添加
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        builder.retryOnConnectionFailure(true);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(base_url)
                .build();
        return retrofit.create(service);
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
