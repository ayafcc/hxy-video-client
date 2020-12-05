package cn.mahua.vod.bean;

import java.util.List;

public class MessageBean {


    /**
     * limit : 5
     * list : [{"id":1,"title":"垃圾分类","create_date":"2020-04-29 00:00:00","content":"test"},{"id":2,"title":"\u201c变废为宝\u201d创意小礼品送给最爱的老师","create_date":"2020-04-29 00:00:00","content":"test"}]
     */

    private String limit;
    private List<ListBean> list;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
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
}
