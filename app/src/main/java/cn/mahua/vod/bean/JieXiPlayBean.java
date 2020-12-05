package cn.mahua.vod.bean;

public class JieXiPlayBean {

    /**
     * code : 200
     * success : 1
     * type : mp4
     * player : dplayer
     * url : http://vodpc-al.wasu.cn/pcsan24/pc/series/2019-09/04/20190904161915931D3ncVXv9.mp4?auth_key=cf7f71fc799c5e8d639d6b4e8db195c1-1575605625-7d8a06295f778f8fc94b76e77aab16e2-&sk=2bb7b451b21b8c07380d48dbdcc82a78
     */

    private String code;
    private String success;
    private String type;
    private String player;
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JieXiPlayBean{" +
                "code='" + code + '\'' +
                ", success='" + success + '\'' +
                ", type='" + type + '\'' +
                ", player='" + player + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
