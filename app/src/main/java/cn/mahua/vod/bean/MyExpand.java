package cn.mahua.vod.bean;

import java.util.List;

public class MyExpand {
    /**
     * limit : 20
     * page : 1
     * total : 2
     * list : [{"user_id":251,"user_name":"12345678917","user_nick_name":"萝卜视频5eyui","user_reg_time":1588856225},{"user_id":250,"user_name":"12345678916","user_nick_name":"萝卜视频9lpop","user_reg_time":1588856200}]
     */

    private int limit;
    private int page;
    private int total;
    private List<ListBean> list;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * user_id : 251
         * user_name : 12345678917
         * user_nick_name : 萝卜视频5eyui
         * user_reg_time : 1588856225
         */

        private int user_id;
        private String user_name;
        private String user_nick_name;
        private int user_reg_time;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_nick_name() {
            return user_nick_name;
        }

        public void setUser_nick_name(String user_nick_name) {
            this.user_nick_name = user_nick_name;
        }

        public int getUser_reg_time() {
            return user_reg_time;
        }

        public void setUser_reg_time(int user_reg_time) {
            this.user_reg_time = user_reg_time;
        }
    }
}
