package cn.mahua.vod.jiexi;


import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.mahua.vod.App;

public enum JieXiUtils2 {
    INSTANCE;
    private List<JieXiWebView2> webViewList;
    private HashSet<Integer> failSet;
    //创建基本线程池
    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100));

    JieXiUtils2() {
        webViewList = new ArrayList<>();
        failSet = new HashSet<Integer>();
    }

    public void getPlayUrl(String url, String vod_url, int curParseIndex, BackListener backListener, int curFailIndex) {
        if (curParseIndex == 0) {
            failSet.clear();
        }
        if (curFailIndex != (-1)) {
            failSet.add(curFailIndex);
        }
        stopGet();//开始下一次解析时候 停止之前的解析
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(vod_url)) return;
        ArrayList<String> urlList = getUrlList(url);
        System.out.println("===Jiexi urlList.size=" + urlList.size());
        boolean hasNew = false;
        if (failSet.size() >= urlList.size()) {
            hasNew = true;
        }

//        for (int i = 0; i <= (urlList.size() - 1); i++) {
//            if (failSet.contains(i)) {
//                for (Integer str : failSet) {
//                    System.out.println("===Jiexi failList" + str + " i=" + i);
//                }
//            } else {
//                System.out.println("===Jiexi getPlayUrlByWebView");
//                getPlayUrlByWebView(urlList.get(i), vod_url, i, urlList.size(), backListener, hasNew);
//            }
//        }
        if(curParseIndex < urlList.size()) {
            getPlayUrlByWebView(urlList.get(curParseIndex), vod_url, curParseIndex, urlList.size(), backListener, hasNew);
        }
        if (failSet.size() >= urlList.size()) {
            System.out.println("=1=1 onError");
            backListener.onError();
//            Intent intent = new Intent("cn.whiner.av.AvVideoController");
//            intent.putExtra("isFromHead",true);
//            LocalBroadcastManager.getInstance(App.getInstance().getContext()).sendBroadcast(intent);
        }

        StringBuilder sb = new StringBuilder();
        Iterator<Integer> iterator = failSet.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next() + ",");
        }
        System.out.println("failList 集合：" + sb.toString());
    }

    private void getPlayUrlByWebView(String parse, String vod_url, int curIndex, int size, BackListener backListener, boolean isEnd) {
        JieXiWebView2 webView = new JieXiWebView2(App.getInstance().getApplicationContext(), parse, backListener);
        System.out.println("===Jiexi curIndex=" + curIndex + "  weburl=" + parse + vod_url);
       if(parse.contains("ys.luob.app")){
           webView.startParse( vod_url, curIndex, size, isEnd);
       }else {
            webView.startParse(parse + vod_url, curIndex, size, isEnd);
        }
        webViewList.add(webView);
    }

    public void stopGet() {
        for (JieXiWebView2 webView : webViewList) {
            webView.destroy();
        }
        webViewList.clear();
    }

    private ArrayList<String> getUrlList(String parses) {
        ArrayList<String> urlStrs = new ArrayList<>();
        if (parses.contains(",")) {
            String[] split = parses.split(",");
            for (String url : split) {
                urlStrs.add(url);
            }
        } else {
            urlStrs.add(parses);
        }
        return urlStrs;
    }
}
