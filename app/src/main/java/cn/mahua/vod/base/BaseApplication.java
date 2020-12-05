package cn.mahua.vod.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;

import java.lang.ref.WeakReference;


public abstract class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private Context mContext;
    private static WeakReference<Context> mContextWeakReference;

    public Context getContext() {
        if (mContextWeakReference == null || mContextWeakReference.get() == null) {
            synchronized (this) {
                if (mContextWeakReference == null || mContextWeakReference.get() == null) {
                    mContextWeakReference = new WeakReference<>(mContext);
                }
            }
        }
        return mContextWeakReference.get();
    }

    public static void setContextRef(Context contextRef) {
        mContextWeakReference = new WeakReference<>(contextRef);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        //工具箱
        Utils.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 配置改变时触发这个方法,例如屏幕方向切换.
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG,"配置改变触发:"+newConfig.toString());
    }

    /**
     * 内存匮乏时会调用.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e(TAG,"内存匮乏");
    }

    /**
     * 每次计算进程优先级满足条件时会调用,回调不同的内存状态.
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e(TAG,"内存状态："+level);
    }

}
