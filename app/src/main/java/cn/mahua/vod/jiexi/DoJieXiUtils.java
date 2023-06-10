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
public enum DoJieXiUtils {
    INSTANCE;
    private List<Downjiexi> webViewList;
    private HashSet<Integer> failSet;
    //创建基本线程池
    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100));
    DoJieXiUtils() {
        webViewList = new ArrayList<>();
        failSet = new HashSet<Integer>();
    }
    public void getPlayUrl(String url, String vod_url, int curParseIndex, BackListener backListener, int curFailIndex) {
       //暂时只支持第一条线路 之后再加

    }
}
