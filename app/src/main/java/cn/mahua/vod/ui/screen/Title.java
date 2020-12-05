package cn.mahua.vod.ui.screen;

public class Title {
    private boolean isSelected;
    private String title;
    private String value;

    public Title(String title,String value) {
        this.title = title;
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
