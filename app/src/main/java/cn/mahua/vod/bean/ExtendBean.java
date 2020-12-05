package cn.mahua.vod.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ExtendBean implements Serializable {

    private static final long serialVersionUID = -4839568986586040403L;

    @SerializedName("class")
    private String zlass;
    private String area;
    private String lang;
    private String year;
    private String star;
    private String director;
    private String state;
    private String version;

    public String getZlass() {
        return zlass;
    }

    public void setZlass(String zlass) {
        this.zlass = zlass;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
