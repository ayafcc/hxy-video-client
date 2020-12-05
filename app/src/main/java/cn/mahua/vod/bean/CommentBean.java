package cn.mahua.vod.bean;

import java.util.List;

public class CommentBean {

    /**
     * comment_id : 7
     * comment_mid : 1
     * comment_rid : 1
     * comment_pid : 0
     * user_id : 9
     * comment_status : 1
     * comment_name : 12345678123
     * comment_ip : 1008565126
     * comment_time : 1565231543
     * comment_content : 不错
     * comment_up : 0
     * comment_down : 0
     * comment_reply : 0
     * comment_report : 0
     * user_portrait : /static/images/touxiang.png
     * sub : []
     * data : null
     */

    private int comment_id;
    private int comment_mid;
    private int comment_rid;
    private int comment_pid;
    private int user_id;
    private int comment_status;
    private String comment_name;
    private int comment_ip;
    private long comment_time;
    private String comment_content;
    private int comment_up;
    private int comment_down;
    private int comment_reply;
    private int comment_report;
    private String user_portrait;
    private Object data;
    private List<?> sub;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getComment_mid() {
        return comment_mid;
    }

    public void setComment_mid(int comment_mid) {
        this.comment_mid = comment_mid;
    }

    public int getComment_rid() {
        return comment_rid;
    }

    public void setComment_rid(int comment_rid) {
        this.comment_rid = comment_rid;
    }

    public int getComment_pid() {
        return comment_pid;
    }

    public void setComment_pid(int comment_pid) {
        this.comment_pid = comment_pid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getComment_status() {
        return comment_status;
    }

    public void setComment_status(int comment_status) {
        this.comment_status = comment_status;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public int getComment_ip() {
        return comment_ip;
    }

    public void setComment_ip(int comment_ip) {
        this.comment_ip = comment_ip;
    }

    public long getComment_time() {
        return comment_time;
    }

    public void setComment_time(long comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public int getComment_up() {
        return comment_up;
    }

    public void setComment_up(int comment_up) {
        this.comment_up = comment_up;
    }

    public int getComment_down() {
        return comment_down;
    }

    public void setComment_down(int comment_down) {
        this.comment_down = comment_down;
    }

    public int getComment_reply() {
        return comment_reply;
    }

    public void setComment_reply(int comment_reply) {
        this.comment_reply = comment_reply;
    }

    public int getComment_report() {
        return comment_report;
    }

    public void setComment_report(int comment_report) {
        this.comment_report = comment_report;
    }

    public String getUser_portrait() {
        return user_portrait;
    }

    public void setUser_portrait(String user_portrait) {
        this.user_portrait = user_portrait;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<?> getSub() {
        return sub;
    }

    public void setSub(List<?> sub) {
        this.sub = sub;
    }
}
