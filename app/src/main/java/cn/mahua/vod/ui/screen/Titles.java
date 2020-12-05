package cn.mahua.vod.ui.screen;

import java.util.List;

public class Titles {

    private List<Title> titles;

    private Title curTitle;

    private int curPos;

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public Title getCurTitle() {
        return curTitle;
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int currentPos){
        curPos=currentPos;
    }

    public void setCurTitle(Title curTitle) {
        if (curTitle == null) return;
        boolean b = false;
        for (int i = 0; i < titles.size(); i++) {
            Title title = titles.get(i);
            if (title.equals(curTitle)) {
                this.curTitle = curTitle;
                this.curTitle.setSelected(true);
                this.curPos = i;
                b = true;
            } else {
                title.setSelected(false);
            }
        }
//        for (Title title : titles) {
//            if (title.equals(curTitle)) {
//                this.curTitle = curTitle;
//                this.curTitle.setSelected(true);
//                b = true;
//            } else {
//                title.setSelected(false);
//            }
//        }
        if (!b) {
            this.curTitle = titles.get(0);
            this.curTitle.setSelected(true);
        }
    }


    public void setTitle(String s) {
//        for (Title title : titles) {
//            if (title.getTitle().equals(s)) {
//                curTitle = title;
//                curTitle.setSelected(true);
//                return;
//            }
//        }

        for (int i = 0; i < titles.size(); i++) {
            Title title = titles.get(i);
            if (title.getTitle().equals(s)) {
                curTitle = title;
                curTitle.setSelected(true);
                curPos = i;
            }
        }
    }

}
