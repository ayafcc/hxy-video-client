package cn.mahua.vod.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 奈蜇 on 2018-09-19.
 * X
 */
public class StringUtils {
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 正则表达式匹配两个指定字符串中间的内容
     * @param soap
     * @return
     */
    public static List<String> getSubUtil(String soap, String rgex){
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 0;
            list.add(m.group(i));
            i++;
        }
        return list;
    }
}
