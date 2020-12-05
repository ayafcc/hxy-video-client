package cn.mahua.vod.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class CardBean implements Serializable {

    private static final long serialVersionUID = -2762598421940579987L;

    @SerializedName(value = "name", alternate = "cat_name")
    private String title;

    private List<VodBean> vods;

    private int page = 1;

    public CardBean() {
    }

    public CardBean(String title, List<VodBean> vodBeanList) {
        this.title = title;
        this.vods = vodBeanList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VodBean> getVods() {
        return vods;
    }

    public void setVods(List<VodBean> vodBeanList) {
        this.vods = vodBeanList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
