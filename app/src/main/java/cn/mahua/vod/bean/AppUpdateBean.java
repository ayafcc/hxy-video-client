package cn.mahua.vod.bean;

public class AppUpdateBean {

    /**
     * app_version_id : 2
     * os : 1
     * version : v1.0.1
     * summary : 1.aaaaaaa 2.aaaaaaa 3.aaaaaaaa 4.aaaaaaaaaa 5.aaaaaa
     * url : http://baidu.com
     * create_time : 1572790342
     * update_time : 1572790342
     * is_required : 1
     * type : 1
     */

    private int app_version_id;
    private String os;
    private String version;
    private String summary;
    private String url;
    private int create_time;
    private int update_time;
    private int is_required;
    private int type;

    public int getApp_version_id() {
        return app_version_id;
    }

    public void setApp_version_id(int app_version_id) {
        this.app_version_id = app_version_id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public int getIs_required() {
        return is_required;
    }

    public void setIs_required(int is_required) {
        this.is_required = is_required;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
