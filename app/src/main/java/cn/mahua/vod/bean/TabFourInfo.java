package cn.mahua.vod.bean;

import java.util.List;

public class TabFourInfo {

    /**
     * limit : 1
     * start : null
     * list : [{"id":1,"name":"福利","img":"1","url":"http://baidu.com"}]
     */

    private String limit;
    private Object start;
    private List<ListBean> list;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Object getStart() {
        return start;
    }

    public void setStart(Object start) {
        this.start = start;
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
         * name : 福利
         * img : 1
         * url : http://baidu.com
         */

        private int id;
        private String name;
        private String img;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
