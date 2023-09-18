package com.sweetieplayer.vod.bean;

import java.io.Serializable;
import java.util.List;

public class SaveLiveDate implements Serializable {
    public final List<LiveBean> list;

    public SaveLiveDate(List<LiveBean> list) {
        this.list = list;
    }
}
