package com.sweetieplayer.vod.bean;

public class UpdateEvent {

    public UpdateEvent(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public final boolean isScroll;
}
