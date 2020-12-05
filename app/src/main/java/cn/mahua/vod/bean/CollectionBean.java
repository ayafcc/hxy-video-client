package cn.mahua.vod.bean;

public class CollectionBean {

    /**
     * ulog_id : 27
     * user_id : 2
     * ulog_mid : 1
     * ulog_type : 2
     * ulog_rid : 1
     * ulog_sid : 0
     * ulog_nid : 0
     * ulog_points : 0
     * ulog_time : 1560877169
     * data : {"id":1,"name":"笑傲江湖第一季","pic":"/tu.php?a=http://pic6.iqiyipic.com/image/20190403/0a/49/a_100003138_m_601_m2_180_236.jpg","link":"/index.php/vod/detail/id/1.html","type":{"type_id":3,"type_name":"综艺","link":"/index.php/vod/type/id/3.html"}}
     * user_name : xzb950330
     */

    private int ulog_id;
    private int user_id;
    private int ulog_mid;
    private int ulog_type;
    private int ulog_rid;
    private int ulog_sid;
    private int ulog_nid;
    private int ulog_points;
    private int ulog_time;
    private DataBean data;
    private String user_name;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getUlog_id() {
        return ulog_id;
    }

    public void setUlog_id(int ulog_id) {
        this.ulog_id = ulog_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUlog_mid() {
        return ulog_mid;
    }

    public void setUlog_mid(int ulog_mid) {
        this.ulog_mid = ulog_mid;
    }

    public int getUlog_type() {
        return ulog_type;
    }

    public void setUlog_type(int ulog_type) {
        this.ulog_type = ulog_type;
    }

    public int getUlog_rid() {
        return ulog_rid;
    }

    public void setUlog_rid(int ulog_rid) {
        this.ulog_rid = ulog_rid;
    }

    public int getUlog_sid() {
        return ulog_sid;
    }

    public void setUlog_sid(int ulog_sid) {
        this.ulog_sid = ulog_sid;
    }

    public int getUlog_nid() {
        return ulog_nid;
    }

    public void setUlog_nid(int ulog_nid) {
        this.ulog_nid = ulog_nid;
    }

    public int getUlog_points() {
        return ulog_points;
    }

    public void setUlog_points(int ulog_points) {
        this.ulog_points = ulog_points;
    }

    public int getUlog_time() {
        return ulog_time;
    }

    public void setUlog_time(int ulog_time) {
        this.ulog_time = ulog_time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 笑傲江湖第一季
         * pic : /tu.php?a=http://pic6.iqiyipic.com/image/20190403/0a/49/a_100003138_m_601_m2_180_236.jpg
         * link : /index.php/vod/detail/id/1.html
         * type : {"type_id":3,"type_name":"综艺","link":"/index.php/vod/type/id/3.html"}
         */

        private int id;
        private String name;
        private String pic;
        private String pic_thumb;
        private String link;
        private TypeBean type;

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

        public String getPic() {
            if (pic_thumb == null || pic_thumb.isEmpty()) {
                return pic;
            } else {
                return getPic_thumb();
            }
        }

        public String getPic_thumb() {
            return pic_thumb;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public TypeBean getType() {
            return type;
        }

        public void setType(TypeBean type) {
            this.type = type;
        }

        public static class TypeBean {
            /**
             * type_id : 3
             * type_name : 综艺
             * link : /index.php/vod/type/id/3.html
             */

            private int type_id;
            private String type_name;
            private String link;

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
