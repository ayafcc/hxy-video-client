package cn.mahua.vod.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class PlayScoreBean extends LitePalSupport {

    private int vodId;
    private int typeId;
    private String vodName;
    private String vodImgUrl;
    private String vodSelectedWorks;
    private float percentage;
    private long curProgress;
    private int urlIndex;
    private int playSourceIndex;
    @Column(ignore = true)
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public long getCurProgress() {
        return curProgress;
    }

    public void setCurProgress(long curProgress) {
        this.curProgress = curProgress;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getVodSelectedWorks() {
        return vodSelectedWorks;
    }

    public void setVodSelectedWorks(String vodSelectedWorks) {
        this.vodSelectedWorks = vodSelectedWorks;
    }

    public String getVodName() {
        return vodName;
    }

    public void setVodName(String vodName) {
        this.vodName = vodName;
    }

    public String getVodImgUrl() {
        return vodImgUrl;
    }

    public void setVodImgUrl(String vodImgUrl) {
        this.vodImgUrl = vodImgUrl;
    }

    public int getVodId() {
        return vodId;
    }

    public void setVodId(int vodId) {
        this.vodId = vodId;
    }

    public int getUrlIndex() {
        return urlIndex;
    }

    public void setUrlIndex(int urlIndex) {
        this.urlIndex = urlIndex;
    }

    public int getPlaySourceIndex() {
        return playSourceIndex;
    }

    public void setPlaySourceIndex(int playSourceIndex) {
        this.playSourceIndex = playSourceIndex;
    }
}
