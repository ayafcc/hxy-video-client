package cn.mahua.vod.jiexi;


import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import cn.mahua.vod.App;

public enum JieXiUtils {
    INSTANCE;
    private List<JieXiWebView> webViewList;

    JieXiUtils() {
        webViewList = new ArrayList<>();
    }

    public void getPlayUrl(String url, String vod_url, int curParseIndex, BackListener backListener) {
        stopGet();//开始下一次解析时候 停止之前的解析
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(vod_url)) return;
        getPlayUrlByWebView(url, vod_url, curParseIndex,backListener);

    }

    private void getPlayUrlByWebView(String parse, String vod_url,int curParseIndex, BackListener backListener) {
        JieXiWebView webView = new JieXiWebView(App.getInstance().getApplicationContext(), parse, vod_url,curParseIndex, backListener);
        webView.startParse();
        webViewList.add(webView);
    }

    public void stopGet() {
        for (JieXiWebView webView : webViewList) {
            webView.destroy();
        }
        webViewList.clear();
    }
}
