package jaygoo.library.m3u8downloader.utils;

import android.text.TextUtils;

public class HttpUtils {

    public static boolean isUrl(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        str = str.trim();
        return str.matches("^(http|https)://.+");
    }

    /**
     * 给出一个地址得到 域名
     *
     * @param url https://video.kssznuu.cn/20200919/Z28BZyZs/index.m3u8
     * @return https://video.kssznuu.cn
     */
    public static String getBasePath(String url) {

        String mUrl = url.replace("//", "&&");
        mUrl = mUrl.substring(0, mUrl.indexOf("/"));
        return mUrl.replace("&&", "//");

    }

    /**
     * 地址合并
     *
     * @param start https://video.kssznuu.cn/20200919/Z28BZyZs/index.m3u8
     * @param end   /20200919/Z28BZyZs/1000kb/hls/index.m3u8
     * @return https://video.kssznuu.cn/20200919/Z28BZyZs/1000kb/hls/index.m3u8
     */
    public static String mergeUrl(String start, String end) {
        if (end.startsWith("/"))
            end = end.replaceFirst("/", "");
        for (String s1 : end.split("/")) {
            if (start.contains(s1))
                start = start.replace(s1 + "/", "");
        }
        return start + end;
    }

}
