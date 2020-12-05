package cn.mahua.vod.ad;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;

public class AdJavascriptInterface {

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void onClickImg(String imageUrl) {
        System.out.println("KEY_START_BEAN ==onClickImg");
        //根据URL查看大图逻辑
        LogUtils.e(imageUrl);
//        if (mListener != null) {
//            mListener.onAdClick(imageUrl);
//        }
    }
}
