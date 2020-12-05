package cn.mahua.vod.bean;

import java.io.Serializable;
import java.util.List;

public class TopicDetailBean implements Serializable {

    String topic_name;
    String topic_pic;
    String topic_blurb;
    String topic_content;
    String topic_tag;

    private List<VodBean> vod_list;

   // private int page = 1;

    public TopicDetailBean() {
    }

    public TopicDetailBean(String title, List<VodBean> vodBeanList) {
        this.topic_tag = title;
        this.vod_list = vodBeanList;
    }

    public String getTitle() {
        return topic_tag;
    }

    public void setTitle(String title) {
        this.topic_tag = title;
    }

    public List<VodBean> getVods() {
        return vod_list;
    }

    public void setVods(List<VodBean> vodBeanList) {
        this.vod_list = vodBeanList;
    }

//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_pic() {
        return topic_pic;
    }

    public void setTopic_pic(String topic_pic) {
        this.topic_pic = topic_pic;
    }

    public String getTopic_blurb() {
        return topic_blurb;
    }

    public void setTopic_blurb(String topic_blurb) {
        this.topic_blurb = topic_blurb;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

}
