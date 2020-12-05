package cn.mahua.vod.ui.filtrate;

import java.util.List;

public class FiltrateBar {

    private String title;
    private List<String> list;
    private String curItem;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getCurItem() {
        return curItem;
    }

    public void setCurItem(String curItem) {
        this.curItem = curItem;
    }
}
