package cn.mahua.vod.bean;

import java.util.List;

public class GroupChatBean {

    /**
     * limit : null
     * list : [{"id":1,"title":"POTATO群","url":"http://www.baidu.com"},{"id":2,"title":"纸飞机群","url":"http://baidu.com"}]
     */

    private Object limit;
    private List<ListBean> list;

    public Object getLimit() {
        return limit;
    }

    public void setLimit(Object limit) {
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
         * title : POTATO群
         * url : http://www.baidu.com
         */

        private int id;
        private String title;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
