package com.sweetieplayer.vod.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.sweetieplayer.vod.banner.BannerData;
import com.sweetieplayer.vod.ui.home.Vod;

@SuppressWarnings("unused")
public class VodBean implements BannerData, Vod, Parcelable {

    private int vod_id;// 视频ID
    private int type_id; // 类型ID
    private int type_id_1;
    private int group_id;
    private String vod_name; // 视频名称
    private String vod_sub;  // 视频副标
    private String vod_en;// 视频别名
    private int vod_status;// 视频审核状态
    private String vod_letter;// 视频首字母
    private String vod_color; // 视频高亮颜色
    private String vod_tag;// 视频tag
    private String vod_class; // 视频扩展分类
    private String vod_pic; // 图片
    private String vod_pic_thumb; // 缩略图
    private String vod_pic_slide;// 海报
    private String vod_actor;// 主演
    private String vod_director;
    private String vod_writer;
    private String vod_behind;
    private String vod_blurb;
    private String vod_remarks;
    private String vod_pubdate;
    private int vod_total;
    private String vod_serial;
    private String vod_tv;
    private String vod_weekday;
    private String vod_area;
    private String vod_lang;
    private String vod_year;
    private String vod_version;
    private String vod_state;
    private String vod_author;
    private String vod_jumpurl;
    private String vod_tpl;
    private String vod_tpl_play;
    private String vod_tpl_down;
    private int vod_isend;
    private int vod_lock;
    private int vod_level;
    private int vod_copyright;
    private int vod_points;
    private int vod_points_play;
    private int vod_points_down;
    private int vod_hits; // 播放次数
    private int vod_hits_day;
    private int vod_hits_week;
    private int vod_hits_month;
    private String vod_duration;
    private int vod_up;
    private int vod_down;
    private String vod_score;
    private int vod_score_all;
    private int vod_score_num;
    private int vod_time;
    private int vod_time_add;
    private int vod_time_hits;
    private int vod_time_make;
    private int vod_trysee;
    private int vod_douban_id;
    private String vod_douban_score;
    private String vod_reurl;
    private String vod_rel_vod;
    private String vod_rel_art;
    private String vod_pwd;
    private String vod_pwd_url;
    private String vod_pwd_play;
    private String vod_pwd_play_url;
    private String vod_pwd_down;
    private String vod_pwd_down_url;
    private String vod_content;
    private String vod_play_from;
    private String vod_play_server;
    private String vod_play_note;
    private String vod_play_url;
    //    private List<UrlBean> vod_urls;
    private String vod_down_from;
    private String vod_down_server;
    private String vod_down_note;
    private String vod_down_url;
    private List<PlayFromBean> vod_play_list;
    private int vod_is_fiery;
    private int vod_is_hot;
    private String vod_custom_tag;
    private TypeBean type;
    private String type_1;
    //    private List<VodBean> rel_vods;// 关联视频 数据结构同视频列表，不包含分页数据结构
    private int comment_num; // 评论数量
    /**
     * playInfo : {"vod_id":50089,"nid":"第4集","\u201ccurProgress\u201d":27280,"\u201csource\u201d":"\u201c爱奇艺\u201d"}
     */

    private PlayInfoBean playInfo;

    @Override
    public String toString() {
        return "VodBean{" +
                "vod_id=" + vod_id +
                ", type_id=" + type_id +
                ", type_id_1=" + type_id_1 +
                ", group_id=" + group_id +
                ", vod_name='" + vod_name + '\'' +
                ", vod_sub='" + vod_sub + '\'' +
                ", vod_en='" + vod_en + '\'' +
                ", vod_status=" + vod_status +
                ", vod_letter='" + vod_letter + '\'' +
                ", vod_color='" + vod_color + '\'' +
                ", vod_tag='" + vod_tag + '\'' +
                ", vod_class='" + vod_class + '\'' +
                ", vod_pic='" + vod_pic + '\'' +
                ", vod_pic_thumb='" + vod_pic_thumb + '\'' +
                ", vod_pic_slide='" + vod_pic_slide + '\'' +
                ", vod_actor='" + vod_actor + '\'' +
                ", vod_director='" + vod_director + '\'' +
                ", vod_writer='" + vod_writer + '\'' +
                ", vod_behind='" + vod_behind + '\'' +
                ", vod_blurb='" + vod_blurb + '\'' +
                ", vod_remarks='" + vod_remarks + '\'' +
                ", vod_pubdate='" + vod_pubdate + '\'' +
                ", vod_total=" + vod_total +
                ", vod_serial='" + vod_serial + '\'' +
                ", vod_tv='" + vod_tv + '\'' +
                ", vod_weekday='" + vod_weekday + '\'' +
                ", vod_area='" + vod_area + '\'' +
                ", vod_lang='" + vod_lang + '\'' +
                ", vod_year='" + vod_year + '\'' +
                ", vod_version='" + vod_version + '\'' +
                ", vod_state='" + vod_state + '\'' +
                ", vod_author='" + vod_author + '\'' +
                ", vod_jumpurl='" + vod_jumpurl + '\'' +
                ", vod_tpl='" + vod_tpl + '\'' +
                ", vod_tpl_play='" + vod_tpl_play + '\'' +
                ", vod_tpl_down='" + vod_tpl_down + '\'' +
                ", vod_isend=" + vod_isend +
                ", vod_lock=" + vod_lock +
                ", vod_level=" + vod_level +
                ", vod_copyright=" + vod_copyright +
                ", vod_points=" + vod_points +
                ", vod_points_play=" + vod_points_play +
                ", vod_points_down=" + vod_points_down +
                ", vod_hits=" + vod_hits +
                ", vod_hits_day=" + vod_hits_day +
                ", vod_hits_week=" + vod_hits_week +
                ", vod_hits_month=" + vod_hits_month +
                ", vod_duration='" + vod_duration + '\'' +
                ", vod_up=" + vod_up +
                ", vod_down=" + vod_down +
                ", vod_score='" + vod_score + '\'' +
                ", vod_score_all=" + vod_score_all +
                ", vod_score_num=" + vod_score_num +
                ", vod_time=" + vod_time +
                ", vod_time_add=" + vod_time_add +
                ", vod_time_hits=" + vod_time_hits +
                ", vod_time_make=" + vod_time_make +
                ", vod_trysee=" + vod_trysee +
                ", vod_douban_id=" + vod_douban_id +
                ", vod_douban_score='" + vod_douban_score + '\'' +
                ", vod_reurl='" + vod_reurl + '\'' +
                ", vod_rel_vod='" + vod_rel_vod + '\'' +
                ", vod_rel_art='" + vod_rel_art + '\'' +
                ", vod_pwd='" + vod_pwd + '\'' +
                ", vod_pwd_url='" + vod_pwd_url + '\'' +
                ", vod_pwd_play='" + vod_pwd_play + '\'' +
                ", vod_pwd_play_url='" + vod_pwd_play_url + '\'' +
                ", vod_pwd_down='" + vod_pwd_down + '\'' +
                ", vod_pwd_down_url='" + vod_pwd_down_url + '\'' +
                ", vod_content='" + vod_content + '\'' +
                ", vod_play_from='" + vod_play_from + '\'' +
                ", vod_play_server='" + vod_play_server + '\'' +
                ", vod_play_note='" + vod_play_note + '\'' +
                ", vod_play_url='" + vod_play_url + '\'' +
                ", vod_down_from='" + vod_down_from + '\'' +
                ", vod_down_server='" + vod_down_server + '\'' +
                ", vod_down_note='" + vod_down_note + '\'' +
                ", vod_down_url='" + vod_down_url + '\'' +
                ", vod_play_list=" + vod_play_list +
                ", vod_is_fiery=" + vod_is_fiery +
                ", vod_is_hot=" + vod_is_hot +
                ", vod_custom_tag='" + vod_custom_tag + '\'' +
                ", type=" + type +
                ", type_1='" + type_1 + '\'' +
                ", comment_num=" + comment_num +
                ", playInfo=" + playInfo +
                '}';
    }

//    public List<UrlBean> getVod_urls() {
//        if (vod_urls == null) {
//            this.vod_urls = new ArrayList();
//            String[] videos = vod_play_url.split("#");
//            for (int i = 0; i < videos.length; i++) {
//                String video = videos[i];
//                String[] videoDetail = video.split("\\$");
//                UrlBean urlBean = new UrlBean();
//                int index = i + 1;
//                urlBean.setNid(index);
//                urlBean.setName(videoDetail[0]);
//                urlBean.setUrl(videoDetail[1]);
//                vod_urls.add(urlBean);
//            }
//        }
//        return vod_urls;
//    }

    public int getVod_id() {
        return vod_id;
    }

    public void setVod_id(int vod_id) {
        this.vod_id = vod_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getType_id_1() {
        return type_id_1;
    }

    public void setType_id_1(int type_id_1) {
        this.type_id_1 = type_id_1;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getVod_name() {
        return vod_name;
    }

    public void setVod_name(String vod_name) {
        this.vod_name = vod_name;
    }

    public String getVod_sub() {
        return vod_sub;
    }

    public void setVod_sub(String vod_sub) {
        this.vod_sub = vod_sub;
    }

    public String getVod_en() {
        return vod_en;
    }

    public void setVod_en(String vod_en) {
        this.vod_en = vod_en;
    }

    public int getVod_status() {
        return vod_status;
    }

    public void setVod_status(int vod_status) {
        this.vod_status = vod_status;
    }

    public String getVod_letter() {
        return vod_letter;
    }

    public void setVod_letter(String vod_letter) {
        this.vod_letter = vod_letter;
    }

    public String getVod_color() {
        return vod_color;
    }

    public void setVod_color(String vod_color) {
        this.vod_color = vod_color;
    }

    public String getVod_tag() {
        return vod_tag;
    }

    public void setVod_tag(String vod_tag) {
        this.vod_tag = vod_tag;
    }

    public String getVod_class() {
        return vod_class;
    }

    public void setVod_class(String vod_class) {
        this.vod_class = vod_class;
    }

    public String getVod_pic() {
        if (getVod_pic_thumb()!=null&&!getVod_pic_thumb().isEmpty()) {
            return getVod_pic_thumb();
        }
        return vod_pic;
    }

    public void setVod_pic(String vod_pic) {
        this.vod_pic = vod_pic;
    }

    public String getVod_pic_thumb() {
        return vod_pic_thumb;
    }

    public void setVod_pic_thumb(String vod_pic_thumb) {
        this.vod_pic_thumb = vod_pic_thumb;
    }

    public String getVod_pic_slide() {
        return vod_pic_slide;
    }

    public void setVod_pic_slide(String vod_pic_slide) {
        this.vod_pic_slide = vod_pic_slide;
    }

    public String getVod_actor() {
        return vod_actor;
    }

    public void setVod_actor(String vod_actor) {
        this.vod_actor = vod_actor;
    }

    public String getVod_director() {
        return vod_director;
    }

    public void setVod_director(String vod_director) {
        this.vod_director = vod_director;
    }

    public String getVod_writer() {
        return vod_writer;
    }

    public void setVod_writer(String vod_writer) {
        this.vod_writer = vod_writer;
    }

    public String getVod_behind() {
        return vod_behind;
    }

    public void setVod_behind(String vod_behind) {
        this.vod_behind = vod_behind;
    }

    public String getVod_blurb() {
        return vod_blurb;
    }

    public void setVod_blurb(String vod_blurb) {
        this.vod_blurb = vod_blurb;
    }

    public String getVod_remarks() {
        return vod_remarks;
    }

    public void setVod_remarks(String vod_remarks) {
        this.vod_remarks = vod_remarks;
    }

    public String getVod_pubdate() {
        return vod_pubdate;
    }

    public void setVod_pubdate(String vod_pubdate) {
        this.vod_pubdate = vod_pubdate;
    }

    public int getVod_total() {
        return vod_total;
    }

    public void setVod_total(int vod_total) {
        this.vod_total = vod_total;
    }

    public String getVod_serial() {
        return vod_serial;
    }

    public void setVod_serial(String vod_serial) {
        this.vod_serial = vod_serial;
    }

    public String getVod_tv() {
        return vod_tv;
    }

    public void setVod_tv(String vod_tv) {
        this.vod_tv = vod_tv;
    }

    public String getVod_weekday() {
        return vod_weekday;
    }

    public void setVod_weekday(String vod_weekday) {
        this.vod_weekday = vod_weekday;
    }

    public String getVod_area() {
        return vod_area;
    }

    public void setVod_area(String vod_area) {
        this.vod_area = vod_area;
    }

    public String getVod_lang() {
        return vod_lang;
    }

    public void setVod_lang(String vod_lang) {
        this.vod_lang = vod_lang;
    }

    public String getVod_year() {
        return vod_year;
    }

    public void setVod_year(String vod_year) {
        this.vod_year = vod_year;
    }

    public String getVod_version() {
        return vod_version;
    }

    public void setVod_version(String vod_version) {
        this.vod_version = vod_version;
    }

    public String getVod_state() {
        return vod_state;
    }

    public void setVod_state(String vod_state) {
        this.vod_state = vod_state;
    }

    public String getVod_author() {
        return vod_author;
    }

    public void setVod_author(String vod_author) {
        this.vod_author = vod_author;
    }

    public String getVod_jumpurl() {
        return vod_jumpurl;
    }

    public void setVod_jumpurl(String vod_jumpurl) {
        this.vod_jumpurl = vod_jumpurl;
    }

    public String getVod_tpl() {
        return vod_tpl;
    }

    public void setVod_tpl(String vod_tpl) {
        this.vod_tpl = vod_tpl;
    }

    public String getVod_tpl_play() {
        return vod_tpl_play;
    }

    public void setVod_tpl_play(String vod_tpl_play) {
        this.vod_tpl_play = vod_tpl_play;
    }

    public String getVod_tpl_down() {
        return vod_tpl_down;
    }

    public void setVod_tpl_down(String vod_tpl_down) {
        this.vod_tpl_down = vod_tpl_down;
    }

    public int getVod_isend() {
        return vod_isend;
    }

    public void setVod_isend(int vod_isend) {
        this.vod_isend = vod_isend;
    }

    public int getVod_lock() {
        return vod_lock;
    }

    public void setVod_lock(int vod_lock) {
        this.vod_lock = vod_lock;
    }

    public int getVod_level() {
        return vod_level;
    }

    public void setVod_level(int vod_level) {
        this.vod_level = vod_level;
    }

    public int getVod_copyright() {
        return vod_copyright;
    }

    public void setVod_copyright(int vod_copyright) {
        this.vod_copyright = vod_copyright;
    }

    public int getVod_points() {
        return vod_points;
    }

    public void setVod_points(int vod_points) {
        this.vod_points = vod_points;
    }

    public int getVod_points_play() {
        return vod_points_play;
    }

    public void setVod_points_play(int vod_points_play) {
        this.vod_points_play = vod_points_play;
    }

    public int getVod_points_down() {
        return vod_points_down;
    }

    public void setVod_points_down(int vod_points_down) {
        this.vod_points_down = vod_points_down;
    }

    public int getVod_hits() {
        return vod_hits;
    }

    public void setVod_hits(int vod_hits) {
        this.vod_hits = vod_hits;
    }

    public int getVod_hits_day() {
        return vod_hits_day;
    }

    public void setVod_hits_day(int vod_hits_day) {
        this.vod_hits_day = vod_hits_day;
    }

    public int getVod_hits_week() {
        return vod_hits_week;
    }

    public void setVod_hits_week(int vod_hits_week) {
        this.vod_hits_week = vod_hits_week;
    }

    public int getVod_hits_month() {
        return vod_hits_month;
    }

    public void setVod_hits_month(int vod_hits_month) {
        this.vod_hits_month = vod_hits_month;
    }

    public String getVod_duration() {
        return vod_duration;
    }

    public void setVod_duration(String vod_duration) {
        this.vod_duration = vod_duration;
    }

    public int getVod_up() {
        return vod_up;
    }

    public void setVod_up(int vod_up) {
        this.vod_up = vod_up;
    }

    public int getVod_down() {
        return vod_down;
    }

    public void setVod_down(int vod_down) {
        this.vod_down = vod_down;
    }

    public String getVod_score() {
        return vod_score;
    }

    public void setVod_score(String vod_score) {
        this.vod_score = vod_score;
    }

    public int getVod_score_all() {
        return vod_score_all;
    }

    public void setVod_score_all(int vod_score_all) {
        this.vod_score_all = vod_score_all;
    }

    public int getVod_score_num() {
        return vod_score_num;
    }

    public void setVod_score_num(int vod_score_num) {
        this.vod_score_num = vod_score_num;
    }

    public int getVod_time() {
        return vod_time;
    }

    public void setVod_time(int vod_time) {
        this.vod_time = vod_time;
    }

    public int getVod_time_add() {
        return vod_time_add;
    }

    public void setVod_time_add(int vod_time_add) {
        this.vod_time_add = vod_time_add;
    }

    public int getVod_time_hits() {
        return vod_time_hits;
    }

    public void setVod_time_hits(int vod_time_hits) {
        this.vod_time_hits = vod_time_hits;
    }

    public int getVod_time_make() {
        return vod_time_make;
    }

    public void setVod_time_make(int vod_time_make) {
        this.vod_time_make = vod_time_make;
    }

    public int getVod_trysee() {
        return vod_trysee;
    }

    public void setVod_trysee(int vod_trysee) {
        this.vod_trysee = vod_trysee;
    }

    public int getVod_douban_id() {
        return vod_douban_id;
    }

    public void setVod_douban_id(int vod_douban_id) {
        this.vod_douban_id = vod_douban_id;
    }

    public String getVod_douban_score() {
        return vod_douban_score;
    }

    public void setVod_douban_score(String vod_douban_score) {
        this.vod_douban_score = vod_douban_score;
    }

    public String getVod_reurl() {
        return vod_reurl;
    }

    public void setVod_reurl(String vod_reurl) {
        this.vod_reurl = vod_reurl;
    }

    public String getVod_rel_vod() {
        return vod_rel_vod;
    }

    public void setVod_rel_vod(String vod_rel_vod) {
        this.vod_rel_vod = vod_rel_vod;
    }

    public String getVod_rel_art() {
        return vod_rel_art;
    }

    public void setVod_rel_art(String vod_rel_art) {
        this.vod_rel_art = vod_rel_art;
    }

    public String getVod_pwd() {
        return vod_pwd;
    }

    public void setVod_pwd(String vod_pwd) {
        this.vod_pwd = vod_pwd;
    }

    public String getVod_pwd_url() {
        return vod_pwd_url;
    }

    public void setVod_pwd_url(String vod_pwd_url) {
        this.vod_pwd_url = vod_pwd_url;
    }

    public String getVod_pwd_play() {
        return vod_pwd_play;
    }

    public void setVod_pwd_play(String vod_pwd_play) {
        this.vod_pwd_play = vod_pwd_play;
    }

    public String getVod_pwd_play_url() {
        return vod_pwd_play_url;
    }

    public void setVod_pwd_play_url(String vod_pwd_play_url) {
        this.vod_pwd_play_url = vod_pwd_play_url;
    }

    public String getVod_pwd_down() {
        return vod_pwd_down;
    }

    public void setVod_pwd_down(String vod_pwd_down) {
        this.vod_pwd_down = vod_pwd_down;
    }

    public String getVod_pwd_down_url() {
        return vod_pwd_down_url;
    }

    public void setVod_pwd_down_url(String vod_pwd_down_url) {
        this.vod_pwd_down_url = vod_pwd_down_url;
    }

    public String getVod_content() {
        return vod_content;
    }

    public void setVod_content(String vod_content) {
        this.vod_content = vod_content;
    }

    public String getVod_play_from() {
        return vod_play_from;
    }

    public void setVod_play_from(String vod_play_from) {
        this.vod_play_from = vod_play_from;
    }

    public String getVod_play_server() {
        return vod_play_server;
    }

    public void setVod_play_server(String vod_play_server) {
        this.vod_play_server = vod_play_server;
    }

    public String getVod_play_note() {
        return vod_play_note;
    }

    public void setVod_play_note(String vod_play_note) {
        this.vod_play_note = vod_play_note;
    }

    public String getVod_play_url() {
        return vod_play_url;
    }

    public void setVod_play_url(String vod_play_url) {
        this.vod_play_url = vod_play_url;

    }

    public String getVod_down_from() {
        return vod_down_from;
    }

    public void setVod_down_from(String vod_down_from) {
        this.vod_down_from = vod_down_from;
    }

    public String getVod_down_server() {
        return vod_down_server;
    }

    public void setVod_down_server(String vod_down_server) {
        this.vod_down_server = vod_down_server;
    }

    public String getVod_down_note() {
        return vod_down_note;
    }

    public void setVod_down_note(String vod_down_note) {
        this.vod_down_note = vod_down_note;
    }

    public String getVod_down_url() {
        return vod_down_url;
    }

    public void setVod_down_url(String vod_down_url) {
        this.vod_down_url = vod_down_url;
    }

    public List<PlayFromBean> getVod_play_list() {
        return vod_play_list;
    }

    public void setVod_play_list(List<PlayFromBean> vod_play_list) {
        this.vod_play_list = vod_play_list;
    }

    public int getVod_is_fiery() {
        return vod_is_fiery;
    }

    public void setVod_is_fiery(int vod_is_fiery) {
        this.vod_is_fiery = vod_is_fiery;
    }

    public int getVod_is_hot() {
        return vod_is_hot;
    }

    public void setVod_is_hot(int vod_is_hot) {
        this.vod_is_hot = vod_is_hot;
    }

    public String getVod_custom_tag() {
        return vod_custom_tag;
    }

    public void setVod_custom_tag(String vod_custom_tag) {
        this.vod_custom_tag = vod_custom_tag;
    }

    public TypeBean getType() {
        return type;
    }

    public void setType(TypeBean type) {
        this.type = type;
    }

    public String getType_1() {
        return type_1;
    }

    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }

//    public List<VodBean> getRel_vods() {
//        return rel_vods;
//    }
//
//    public void setRel_vods(List<VodBean> rel_vods) {
//        this.rel_vods = rel_vods;
//    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    @Override
    public String getBannerImg() {
        return vod_pic_slide;
    }

    @Override
    public String getBannerName() {
        return vod_name;
    }

    @Override
    public String getVodName() {
        return vod_name;
    }

    @Override
    public String getVodBlurb() {
        return vod_blurb == null ? "" : vod_blurb;
    }

    @Override
    public String getVodLang() {
        return vod_lang;
    }

    @Override
    public String getVodRemarks() {
        return vod_remarks;
    }

    @Override
    public String getVodPic() {
        if (getVodPicThumb()!=null&&!getVodPicThumb().isEmpty()) {
            return getVodPicThumb();
        }
        return vod_pic;
    }


    @Override
    public String getVodPicThumb() {
        return vod_pic_thumb;
    }

    @Override
    public String getVodPicSlide() {
        return vod_pic_slide;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.vod_id);
        dest.writeInt(this.type_id);
        dest.writeInt(this.type_id_1);
        dest.writeInt(this.group_id);
        dest.writeString(this.vod_name);
        dest.writeString(this.vod_sub);
        dest.writeString(this.vod_en);
        dest.writeInt(this.vod_status);
        dest.writeString(this.vod_letter);
        dest.writeString(this.vod_color);
        dest.writeString(this.vod_tag);
        dest.writeString(this.vod_class);
        dest.writeString(this.vod_pic);
        dest.writeString(this.vod_pic_thumb);
        dest.writeString(this.vod_pic_slide);
        dest.writeString(this.vod_actor);
        dest.writeString(this.vod_director);
        dest.writeString(this.vod_writer);
        dest.writeString(this.vod_behind);
        dest.writeString(this.vod_blurb);
        dest.writeString(this.vod_remarks);
        dest.writeString(this.vod_pubdate);
        dest.writeInt(this.vod_total);
        dest.writeString(this.vod_serial);
        dest.writeString(this.vod_tv);
        dest.writeString(this.vod_weekday);
        dest.writeString(this.vod_area);
        dest.writeString(this.vod_lang);
        dest.writeString(this.vod_year);
        dest.writeString(this.vod_version);
        dest.writeString(this.vod_state);
        dest.writeString(this.vod_author);
        dest.writeString(this.vod_jumpurl);
        dest.writeString(this.vod_tpl);
        dest.writeString(this.vod_tpl_play);
        dest.writeString(this.vod_tpl_down);
        dest.writeInt(this.vod_isend);
        dest.writeInt(this.vod_lock);
        dest.writeInt(this.vod_level);
        dest.writeInt(this.vod_copyright);
        dest.writeInt(this.vod_points);
        dest.writeInt(this.vod_points_play);
        dest.writeInt(this.vod_points_down);
        dest.writeInt(this.vod_hits);
        dest.writeInt(this.vod_hits_day);
        dest.writeInt(this.vod_hits_week);
        dest.writeInt(this.vod_hits_month);
        dest.writeString(this.vod_duration);
        dest.writeInt(this.vod_up);
        dest.writeInt(this.vod_down);
        dest.writeString(this.vod_score);
        dest.writeInt(this.vod_score_all);
        dest.writeInt(this.vod_score_num);
        dest.writeInt(this.vod_time);
        dest.writeInt(this.vod_time_add);
        dest.writeInt(this.vod_time_hits);
        dest.writeInt(this.vod_time_make);
        dest.writeInt(this.vod_trysee);
        dest.writeInt(this.vod_douban_id);
        dest.writeString(this.vod_douban_score);
        dest.writeString(this.vod_reurl);
        dest.writeString(this.vod_rel_vod);
        dest.writeString(this.vod_rel_art);
        dest.writeString(this.vod_pwd);
        dest.writeString(this.vod_pwd_url);
        dest.writeString(this.vod_pwd_play);
        dest.writeString(this.vod_pwd_play_url);
        dest.writeString(this.vod_pwd_down);
        dest.writeString(this.vod_pwd_down_url);
        dest.writeString(this.vod_content);
        dest.writeString(this.vod_play_from);
        dest.writeString(this.vod_play_server);
        dest.writeString(this.vod_play_note);
        dest.writeString(this.vod_play_url);
        dest.writeString(this.vod_down_from);
        dest.writeString(this.vod_down_server);
        dest.writeString(this.vod_down_note);
        dest.writeString(this.vod_down_url);
        dest.writeTypedList(this.vod_play_list);
        dest.writeInt(this.vod_is_fiery);
        dest.writeInt(this.vod_is_hot);
        dest.writeString(this.vod_custom_tag);
        dest.writeSerializable(this.type);
        dest.writeString(this.type_1);
        dest.writeInt(this.comment_num);
    }

    public VodBean() {
    }

    protected VodBean(Parcel in) {
        this.vod_id = in.readInt();
        this.type_id = in.readInt();
        this.type_id_1 = in.readInt();
        this.group_id = in.readInt();
        this.vod_name = in.readString();
        this.vod_sub = in.readString();
        this.vod_en = in.readString();
        this.vod_status = in.readInt();
        this.vod_letter = in.readString();
        this.vod_color = in.readString();
        this.vod_tag = in.readString();
        this.vod_class = in.readString();
        this.vod_pic = in.readString();
        this.vod_pic_thumb = in.readString();
        this.vod_pic_slide = in.readString();
        this.vod_actor = in.readString();
        this.vod_director = in.readString();
        this.vod_writer = in.readString();
        this.vod_behind = in.readString();
        this.vod_blurb = in.readString();
        this.vod_remarks = in.readString();
        this.vod_pubdate = in.readString();
        this.vod_total = in.readInt();
        this.vod_serial = in.readString();
        this.vod_tv = in.readString();
        this.vod_weekday = in.readString();
        this.vod_area = in.readString();
        this.vod_lang = in.readString();
        this.vod_year = in.readString();
        this.vod_version = in.readString();
        this.vod_state = in.readString();
        this.vod_author = in.readString();
        this.vod_jumpurl = in.readString();
        this.vod_tpl = in.readString();
        this.vod_tpl_play = in.readString();
        this.vod_tpl_down = in.readString();
        this.vod_isend = in.readInt();
        this.vod_lock = in.readInt();
        this.vod_level = in.readInt();
        this.vod_copyright = in.readInt();
        this.vod_points = in.readInt();
        this.vod_points_play = in.readInt();
        this.vod_points_down = in.readInt();
        this.vod_hits = in.readInt();
        this.vod_hits_day = in.readInt();
        this.vod_hits_week = in.readInt();
        this.vod_hits_month = in.readInt();
        this.vod_duration = in.readString();
        this.vod_up = in.readInt();
        this.vod_down = in.readInt();
        this.vod_score = in.readString();
        this.vod_score_all = in.readInt();
        this.vod_score_num = in.readInt();
        this.vod_time = in.readInt();
        this.vod_time_add = in.readInt();
        this.vod_time_hits = in.readInt();
        this.vod_time_make = in.readInt();
        this.vod_trysee = in.readInt();
        this.vod_douban_id = in.readInt();
        this.vod_douban_score = in.readString();
        this.vod_reurl = in.readString();
        this.vod_rel_vod = in.readString();
        this.vod_rel_art = in.readString();
        this.vod_pwd = in.readString();
        this.vod_pwd_url = in.readString();
        this.vod_pwd_play = in.readString();
        this.vod_pwd_play_url = in.readString();
        this.vod_pwd_down = in.readString();
        this.vod_pwd_down_url = in.readString();
        this.vod_content = in.readString();
        this.vod_play_from = in.readString();
        this.vod_play_server = in.readString();
        this.vod_play_note = in.readString();
        this.vod_play_url = in.readString();
        this.vod_down_from = in.readString();
        this.vod_down_server = in.readString();
        this.vod_down_note = in.readString();
        this.vod_down_url = in.readString();
        this.vod_play_list = in.createTypedArrayList(PlayFromBean.CREATOR);
        this.vod_is_fiery = in.readInt();
        this.vod_is_hot = in.readInt();
        this.vod_custom_tag = in.readString();
        this.type = (TypeBean) in.readSerializable();
        this.type_1 = in.readString();
        this.comment_num = in.readInt();
    }

    public static final Creator<VodBean> CREATOR = new Creator<VodBean>() {
        @Override
        public VodBean createFromParcel(Parcel source) {
            return new VodBean(source);
        }

        @Override
        public VodBean[] newArray(int size) {
            return new VodBean[size];
        }
    };

    public PlayInfoBean getPlayInfo() {
        return playInfo;
    }

    public void setPlayInfo(PlayInfoBean playInfo) {
        this.playInfo = playInfo;
    }

    public static class PlayInfoBean {

        @SerializedName("vod_id")
        private int vod_idX;
        private String nid;
        private Long curProgress;
        private String source;
        private int urlIndex;
        private int playSourceIndex;

        public int getVod_idX() {
            return vod_idX;
        }

        public void setVod_idX(int vod_idX) {
            this.vod_idX = vod_idX;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public Long getCurProgress() {
            return curProgress;
        }

        public void setCurProgress(Long curProgress) {
            this.curProgress = curProgress;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getUrlIndex() {
            return urlIndex;
        }

        public void setUrlIndex(int urlIndex) {
            this.urlIndex = urlIndex;
        }

        public int getPlaySourceIndex() {
            return playSourceIndex;
        }

        public void setPlaySourceIndex(int playSourceIndex) {
            this.playSourceIndex = playSourceIndex;
        }
    }
}
