package com.sweetieplayer.vod.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@SuppressWarnings("unused")
public class UrlBean implements Parcelable {


    private String name;
    private String url;
    private String from;
    private int nid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlBean urlBean = (UrlBean) o;
        return nid == urlBean.nid &&
                Objects.equals(name, urlBean.name) &&
                Objects.equals(url, urlBean.url) &&
                Objects.equals(from, urlBean.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, from, nid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.from);
        dest.writeInt(this.nid);
    }

    public UrlBean() {
    }

    protected UrlBean(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.from = in.readString();
        this.nid = in.readInt();
    }

    public static final Parcelable.Creator<UrlBean> CREATOR = new Parcelable.Creator<UrlBean>() {
        @Override
        public UrlBean createFromParcel(Parcel source) {
            return new UrlBean(source);
        }

        @Override
        public UrlBean[] newArray(int size) {
            return new UrlBean[size];
        }
    };

    @Override
    public String toString() {
        return "UrlBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", from='" + from + '\'' +
                ", nid=" + nid +
                '}';
    }
}
