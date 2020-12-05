package cn.mahua.vod.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author kong
 * @description:
 */
public class UrlUtils {
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    /**
     * 将关键字转换成最后转换的url
     *
     * @param keyword
     * @return
     */
    public static String converKeywordLoadOrSearch(String keyword) {
        keyword = keyword.trim();

        if (keyword.startsWith("www.")) {
            keyword = HTTP + keyword;
        } else if (keyword.startsWith("ftp.")) {
            keyword = "ftp://" + keyword;
        }

        boolean containsPeriod = keyword.contains(".");
        boolean isIPAddress = (TextUtils.isDigitsOnly(keyword.replace(".", ""))
                && (keyword.replace(".", "").length() >= 4) && keyword.contains("."));
        boolean aboutScheme = keyword.contains("about:");
        boolean validURL = (keyword.startsWith("ftp://") || keyword.startsWith(HTTP) || keyword.startsWith(HTTPS))
                || isIPAddress;
        boolean isSearch = ((keyword.contains(" ") || !containsPeriod) && !aboutScheme);

        if (isIPAddress
                && (!keyword.startsWith(HTTP) || !keyword.startsWith(HTTPS))) {
            keyword = HTTP + keyword;
        }

        String converUrl;
        if (isSearch) {
            try {
                keyword = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            converUrl = "http://www.baidu.com/s?wd=" + keyword + "&ie=UTF-8";
        } else if (!validURL) {
            converUrl = HTTP + keyword;
        } else {
            converUrl = keyword;
        }
        return converUrl;
    }
}
