package cn.mahua.vod.bean;

import java.util.List;

@SuppressWarnings("unused")
public class BannerBean {

    private List<VodBean> bannerList;

    public BannerBean(List<VodBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<VodBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<VodBean> bannerList) {
        this.bannerList = bannerList;
    }

}
