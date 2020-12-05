package cn.mahua.vod.bean;

import com.google.gson.annotations.SerializedName;

public class UserInfoBean {

    /**
     * user_id : 0
     * user_name : 游客
     * user_portrait : static/images/touxiang.png?s=1574147777
     * group_id : 1
     * points : 0
     * user_pwd : null
     */
    /**
     * user_nick_name :
     * user_qq :
     * user_email :
     * user_phone :
     * user_status : 1
     * user_portrait_thumb :
     * user_openid_qq :
     * user_openid_weixin :
     * user_question :
     * user_answer :
     * user_points : 10
     * user_points_froze : 0
     * user_reg_time : 1560190580
     * user_reg_ip : 2130706433
     * user_login_time : 1560445999
     * user_login_ip : 2130706433
     * user_last_login_time : 1560445893
     * user_last_login_ip : 2130706433
     * user_login_num : 17
     * user_extend : 0
     * user_random : 07edea2d5dcc6fe631d8204dd4161e8d
     * user_end_time : 0
     * user_pid : 0
     * user_pid_2 : 0
     * user_pid_3 : 0
     * user_gold : 29.00
     * group : {"group_id":2,"group_name":"默认会员","group_status":1,"group_type":",1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,","group_points_day":0,"group_points_week":0,"group_points_month":0,"group_points_year":0,"group_points_free":0}
     */

    private int user_id;
    private String user_name;
    private String user_portrait;
    private int group_id;
    private int points;
    private String user_pwd;
    private String user_nick_name;
    private String user_qq;
    private String user_email;
    private String user_phone;
    private int user_status;
    private String user_portrait_thumb;
    private String user_openid_qq;
    private String user_openid_weixin;
    private String user_question;
    private String user_answer;
    private int user_points;
    private int user_points_froze;
    private int user_reg_time;
    private int user_reg_ip;
    private int user_login_time;
    private int user_login_ip;
    private int user_last_login_time;
    private int user_last_login_ip;
    private int user_login_num;
    private int user_extend;
    private String user_random;
    private long user_end_time;
    private int user_pid;
    private int user_pid_2;
    private int user_pid_3;
    private String user_gold;
    private GroupBean group;
    private String user_level;
    private String user_view_count;
    private String leave_peoples;
    private String leave_times;


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

    public String getUser_portrait() {
        return user_portrait;
    }

    public void setUser_portrait(String user_portrait) {
        this.user_portrait = user_portrait;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getUser_qq() {
        return user_qq;
    }

    public void setUser_qq(String user_qq) {
        this.user_qq = user_qq;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getUser_portrait_thumb() {
        return user_portrait_thumb;
    }

    public void setUser_portrait_thumb(String user_portrait_thumb) {
        this.user_portrait_thumb = user_portrait_thumb;
    }

    public String getUser_openid_qq() {
        return user_openid_qq;
    }

    public void setUser_openid_qq(String user_openid_qq) {
        this.user_openid_qq = user_openid_qq;
    }

    public String getUser_openid_weixin() {
        return user_openid_weixin;
    }

    public void setUser_openid_weixin(String user_openid_weixin) {
        this.user_openid_weixin = user_openid_weixin;
    }

    public String getUser_question() {
        return user_question;
    }

    public void setUser_question(String user_question) {
        this.user_question = user_question;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public int getUser_points() {
        return user_points;
    }

    public void setUser_points(int user_points) {
        this.user_points = user_points;
    }

    public int getUser_points_froze() {
        return user_points_froze;
    }

    public void setUser_points_froze(int user_points_froze) {
        this.user_points_froze = user_points_froze;
    }

    public int getUser_reg_time() {
        return user_reg_time;
    }

    public void setUser_reg_time(int user_reg_time) {
        this.user_reg_time = user_reg_time;
    }

    public int getUser_reg_ip() {
        return user_reg_ip;
    }

    public void setUser_reg_ip(int user_reg_ip) {
        this.user_reg_ip = user_reg_ip;
    }

    public int getUser_login_time() {
        return user_login_time;
    }

    public void setUser_login_time(int user_login_time) {
        this.user_login_time = user_login_time;
    }

    public int getUser_login_ip() {
        return user_login_ip;
    }

    public void setUser_login_ip(int user_login_ip) {
        this.user_login_ip = user_login_ip;
    }

    public int getUser_last_login_time() {
        return user_last_login_time;
    }

    public void setUser_last_login_time(int user_last_login_time) {
        this.user_last_login_time = user_last_login_time;
    }

    public int getUser_last_login_ip() {
        return user_last_login_ip;
    }

    public void setUser_last_login_ip(int user_last_login_ip) {
        this.user_last_login_ip = user_last_login_ip;
    }

    public int getUser_login_num() {
        return user_login_num;
    }

    public void setUser_login_num(int user_login_num) {
        this.user_login_num = user_login_num;
    }

    public int getUser_extend() {
        return user_extend;
    }

    public void setUser_extend(int user_extend) {
        this.user_extend = user_extend;
    }

    public String getUser_random() {
        return user_random;
    }

    public void setUser_random(String user_random) {
        this.user_random = user_random;
    }

    public long getUser_end_time() {
        return user_end_time;
    }

    public void setUser_end_time(long user_end_time) {
        this.user_end_time = user_end_time;
    }

    public int getUser_pid() {
        return user_pid;
    }

    public void setUser_pid(int user_pid) {
        this.user_pid = user_pid;
    }

    public int getUser_pid_2() {
        return user_pid_2;
    }

    public void setUser_pid_2(int user_pid_2) {
        this.user_pid_2 = user_pid_2;
    }

    public int getUser_pid_3() {
        return user_pid_3;
    }

    public void setUser_pid_3(int user_pid_3) {
        this.user_pid_3 = user_pid_3;
    }

    public String getUser_gold() {
        return user_gold;
    }

    public void setUser_gold(String user_gold) {
        this.user_gold = user_gold;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public static class GroupBean {
        /**
         * group_id : 2
         * group_name : 默认会员
         * group_status : 1
         * group_type : ,1,6,7,8,9,10,11,12,2,13,14,15,16,3,4,5,17,18,
         * group_points_day : 0
         * group_points_week : 0
         * group_points_month : 0
         * group_points_year : 0
         * group_points_free : 0
         */

        @SerializedName("group_id")
        private int group_idX;
        private String group_name;
        private int group_status;
        private String group_type;
        private int group_points_day;
        private int group_points_week;
        private int group_points_month;
        private int group_points_year;
        private int group_points_free;

        public int getGroup_idX() {
            return group_idX;
        }

        public void setGroup_idX(int group_idX) {
            this.group_idX = group_idX;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public int getGroup_status() {
            return group_status;
        }

        public void setGroup_status(int group_status) {
            this.group_status = group_status;
        }

        public String getGroup_type() {
            return group_type;
        }

        public void setGroup_type(String group_type) {
            this.group_type = group_type;
        }

        public int getGroup_points_day() {
            return group_points_day;
        }

        public void setGroup_points_day(int group_points_day) {
            this.group_points_day = group_points_day;
        }

        public int getGroup_points_week() {
            return group_points_week;
        }

        public void setGroup_points_week(int group_points_week) {
            this.group_points_week = group_points_week;
        }

        public int getGroup_points_month() {
            return group_points_month;
        }

        public void setGroup_points_month(int group_points_month) {
            this.group_points_month = group_points_month;
        }

        public int getGroup_points_year() {
            return group_points_year;
        }

        public void setGroup_points_year(int group_points_year) {
            this.group_points_year = group_points_year;
        }

        public int getGroup_points_free() {
            return group_points_free;
        }

        public void setGroup_points_free(int group_points_free) {
            this.group_points_free = group_points_free;
        }
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getUser_view_count() {
        return user_view_count;
    }

    public void setUser_view_count(String user_view_count) {
        this.user_view_count = user_view_count;
    }

    public String getLeave_peoples() {
        return leave_peoples;
    }

    public void setLeave_peoples(String leave_peoples) {
        this.leave_peoples = leave_peoples;
    }

    public String getLeave_times() {
        return leave_times;
    }

    public void setLeave_times(String leave_times) {
        this.leave_times = leave_times;
    }
}
