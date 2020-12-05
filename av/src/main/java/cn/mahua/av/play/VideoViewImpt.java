package cn.mahua.av.play;

import java.util.LinkedHashMap;

public interface VideoViewImpt {

    LinkedHashMap<String, String> getHdData();

    void switchHd(String hd);

    void setVideoSpeed(float speed);

    /**
     * 显示弹幕
     */
    void showDanmaku();

    /**
     * 隐藏弹幕
     */
    void hideDanmaku();

    /**
     * 发送文字弹幕
     *
     * @param text   弹幕文字
     * @param isSelf 是不是自己发的
     */
    void addDanmaku(String text, boolean isSelf);



}
