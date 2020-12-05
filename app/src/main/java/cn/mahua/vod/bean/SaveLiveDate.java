package cn.mahua.vod.bean;

import java.io.Serializable;
import java.util.List;

public class SaveLiveDate implements Serializable {
    public List<LiveBean> list;

    public SaveLiveDate(List<LiveBean> list) {
        this.list = list;
    }
}
