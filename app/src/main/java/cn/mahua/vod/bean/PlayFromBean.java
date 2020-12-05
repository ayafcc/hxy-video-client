package cn.mahua.vod.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@SuppressWarnings("unused")
public class PlayFromBean implements Parcelable {

    private int sid;
    private PlayerInfoBean player_info;
    private String server_info;
    private String from;
    private String url;
    private String server;
    private String note;
    private int url_count;
    private List<UrlBean> urls;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public PlayerInfoBean getPlayer_info() {
        return player_info;
    }

    public void setPlayer_info(PlayerInfoBean player_info) {
        this.player_info = player_info;
    }

    public String getServer_info() {
        return server_info;
    }

    public void setServer_info(String server_info) {
        this.server_info = server_info;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUrl_count() {
        return url_count;
    }

    public void setUrl_count(int url_count) {
        this.url_count = url_count;
    }

    public List<UrlBean> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlBean> urls) {
        this.urls = urls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sid);
        dest.writeParcelable(this.player_info, flags);
        dest.writeString(this.server_info);
        dest.writeString(this.from);
        dest.writeString(this.url);
        dest.writeString(this.server);
        dest.writeString(this.note);
        dest.writeInt(this.url_count);
        dest.writeTypedList(this.urls);
    }

    public PlayFromBean() {
    }

    protected PlayFromBean(Parcel in) {
        this.sid = in.readInt();
        this.player_info = in.readParcelable(PlayerInfoBean.class.getClassLoader());
        this.server_info = in.readString();
        this.from = in.readString();
        this.url = in.readString();
        this.server = in.readString();
        this.note = in.readString();
        this.url_count = in.readInt();
        this.urls = in.createTypedArrayList(UrlBean.CREATOR);
    }

    public static final Parcelable.Creator<PlayFromBean> CREATOR = new Parcelable.Creator<PlayFromBean>() {
        @Override
        public PlayFromBean createFromParcel(Parcel source) {
            return new PlayFromBean(source);
        }

        @Override
        public PlayFromBean[] newArray(int size) {
            return new PlayFromBean[size];
        }
    };

    @Override
    public String toString() {
        return "PlayFromBean{" +
                "sid=" + sid +
                ", player_info=" + player_info +
                ", server_info='" + server_info + '\'' +
                ", from='" + from + '\'' +
                ", url='" + url + '\'' +
                ", server='" + server + '\'' +
                ", note='" + note + '\'' +
                ", url_count=" + url_count +
                ", urls=" + urls +
                '}';
    }
}
