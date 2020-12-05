package cn.mahua.vod.bean;

public class MessageDetail {


    /**
     * id : 1
     * title : 垃圾分类
     * create_date : 2020-04-29 00:00:00
     * content : test
     */

    private int id;
    private String title;
    private String create_date;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
