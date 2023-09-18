package com.sweetieplayer.vod.jiexi;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class Downjiexi {
    private static final String TAG = "doJieXi--";
    private Handler handler = new Handler();
    private Context mContext;
    static Handler mainHanlder = new Handler(Looper.getMainLooper());
    private boolean mIsEnd = false;
}
