package com.sweetieplayer.vod.bean;

public class GetUrlEvent {
    public final String url;
    public final String title;

    public GetUrlEvent(String url,String title) {
        this.url = url;
        this.title = title;
    }
}
