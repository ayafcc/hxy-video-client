package cn.mahua.vod.bean;


import java.util.List;

@SuppressWarnings("unused")
public class TopBean {

    private String title;
    private List<VodBean> vodList;

    public TopBean(String title, List<VodBean> vodList) {
        this.title = title;
        this.vodList = vodList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VodBean> getVodList() {
        return vodList;
    }

    public void setVodList(List<VodBean> vodList) {
        this.vodList = vodList;
    }
}
