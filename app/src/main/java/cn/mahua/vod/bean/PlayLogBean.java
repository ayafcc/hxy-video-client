package cn.mahua.vod.bean;

public class PlayLogBean {
    String vod_id;
    String nid;
    String source;
    String percent;
    long last_view_time;
    String user_id;
    int type_id;



    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getVod_name() {
        return vod_name;
    }

    public void setVod_name(String vod_name) {
        this.vod_name = vod_name;
    }

    public String getVod_pic() {
        if (pic_thumb==null||pic_thumb.isEmpty()) {
            return vod_pic;
        } else {
            return getPic_thumb();
        }
    }

    public void setVod_pic(String vod_pic) {
        this.vod_pic = vod_pic;
    }

    public String getPic_thumb() {
        return pic_thumb;
    }

    public void setPic_thumb(String pic_thumb) {
        this.pic_thumb = pic_thumb;
    }

    String vod_name;
    String vod_pic;
    String pic_thumb;

    public long curProgress;
    public int urlIndex;
    public int playSourceIndex;



    public String getVod_id() {
        return vod_id;
    }

    public void setVod_id(String vod_id) {
        this.vod_id = vod_id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public long getLast_view_time() {
        return last_view_time;
    }

    public void setLast_view_time(long last_view_time) {
        this.last_view_time = last_view_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
