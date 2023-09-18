package com.sweetieplayer.vod.bean;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("unused")
public class PlayerInfoBean implements Parcelable {

    private String status;
    private String from;
    private String show;
    private String des;
    private String ps;
    private String parse;
    private String parse2;
    private String sort;
    private String tip;
    private String id;

    public String getParse2() {
        return parse2;
    }

    public void setParse2(String parse2) {
        this.parse2 = parse2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getParse() {
        return parse;
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.from);
        dest.writeString(this.show);
        dest.writeString(this.des);
        dest.writeString(this.ps);
        dest.writeString(this.parse);
        dest.writeString(this.parse2);
        dest.writeString(this.sort);
        dest.writeString(this.tip);
        dest.writeString(this.id);
    }

    public PlayerInfoBean() {
    }

    protected PlayerInfoBean(Parcel in) {
        this.status = in.readString();
        this.from = in.readString();
        this.show = in.readString();
        this.des = in.readString();
        this.ps = in.readString();
        this.parse = in.readString();
        this.parse2 = in.readString();
        this.sort = in.readString();
        this.tip = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<PlayerInfoBean> CREATOR = new Parcelable.Creator<PlayerInfoBean>() {
        @Override
        public PlayerInfoBean createFromParcel(Parcel source) {
            return new PlayerInfoBean(source);
        }

        @Override
        public PlayerInfoBean[] newArray(int size) {
            return new PlayerInfoBean[size];
        }
    };

    @Override
    public String toString() {
        return "PlayerInfoBean{" +
                "status='" + status + '\'' +
                ", from='" + from + '\'' +
                ", show='" + show + '\'' +
                ", des='" + des + '\'' +
                ", ps='" + ps + '\'' +
                ", parse='" + parse + '\'' +
                ", parse2='" + parse2 + '\'' +
                ", sort='" + sort + '\'' +
                ", tip='" + tip + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
