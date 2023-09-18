package com.sweetieplayer.vod.jiexi;
public class StringUrlseek {
    public static String seekurl(String url) {
        String reaurl = "";
        int pplast = url.length();
        if (url.indexOf("=http") != -1 || url.indexOf("=https") != -1 || url.indexOf("=https%3A%2F") != -1 || url.indexOf("=http%3A%2F") != -1) {
            reaurl = url.substring(url.lastIndexOf("http"), pplast);

        }else{
            reaurl = url;
        }
        return reaurl;
    }
    public static String seek_header(String url) {
        String reaurl = url;
        int pplast = url.length();
            //reaurl=url.replaceFirst("iktoken=","");
            reaurl = url.substring(url.lastIndexOf("http"), url.lastIndexOf("&url"));
        return reaurl;
    }
}
