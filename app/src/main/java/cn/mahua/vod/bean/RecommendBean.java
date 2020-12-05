package cn.mahua.vod.bean;

import java.util.List;

/**
 * Autor: LiQingfeng
 * Date: 2019/11/12
 * Desc:
 **/
public class RecommendBean {
    private List<VodBean> vodBeanList;
    private int type; //0 或 1

    public RecommendBean(List<VodBean> vodBeanList, int type) {
        this.vodBeanList = vodBeanList;
        this.type = type;
    }

    public List<VodBean> getVodBeanList() {
        return vodBeanList;
    }

    public void setVodBeanList(List<VodBean> vodBeanList) {
        this.vodBeanList = vodBeanList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
