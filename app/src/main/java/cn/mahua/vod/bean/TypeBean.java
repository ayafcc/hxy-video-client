package cn.mahua.vod.bean;

import java.util.List;

import cn.mahua.vod.ui.home.Type;

@SuppressWarnings("unused")
public class TypeBean implements Type {

    private static final long serialVersionUID = -4095046421974722992L;

    private int type_id;
    private String type_name;
    private String type_en;
    private int type_sort;
    private int type_mid;
    private int type_pid;
    private int type_status;
    private String type_tpl;
    private String type_tpl_list;
    private String type_tpl_detail;
    private String type_tpl_play;
    private String type_tpl_down;
    private String type_key;
    private String type_des;
    private String type_title;
    private String type_union;
    private ExtendBean type_extend;
    private String childids;
    private List<CardBean> classes;

    public TypeBean() {
    }

    public TypeBean(String type_name) {
        this.type_name = type_name;
    }


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

    public String getType_en() {
        return type_en;
    }

    public void setType_en(String type_en) {
        this.type_en = type_en;
    }

    public int getType_sort() {
        return type_sort;
    }

    public void setType_sort(int type_sort) {
        this.type_sort = type_sort;
    }

    public int getType_mid() {
        return type_mid;
    }

    public void setType_mid(int type_mid) {
        this.type_mid = type_mid;
    }

    public int getType_pid() {
        return type_pid;
    }

    public void setType_pid(int type_pid) {
        this.type_pid = type_pid;
    }

    public int getType_status() {
        return type_status;
    }

    public void setType_status(int type_status) {
        this.type_status = type_status;
    }

    public String getType_tpl() {
        return type_tpl;
    }

    public void setType_tpl(String type_tpl) {
        this.type_tpl = type_tpl;
    }

    public String getType_tpl_list() {
        return type_tpl_list;
    }

    public void setType_tpl_list(String type_tpl_list) {
        this.type_tpl_list = type_tpl_list;
    }

    public String getType_tpl_detail() {
        return type_tpl_detail;
    }

    public void setType_tpl_detail(String type_tpl_detail) {
        this.type_tpl_detail = type_tpl_detail;
    }

    public String getType_tpl_play() {
        return type_tpl_play;
    }

    public void setType_tpl_play(String type_tpl_play) {
        this.type_tpl_play = type_tpl_play;
    }

    public String getType_tpl_down() {
        return type_tpl_down;
    }

    public void setType_tpl_down(String type_tpl_down) {
        this.type_tpl_down = type_tpl_down;
    }

    public String getType_key() {
        return type_key;
    }

    public void setType_key(String type_key) {
        this.type_key = type_key;
    }

    public String getType_des() {
        return type_des;
    }

    public void setType_des(String type_des) {
        this.type_des = type_des;
    }

    public String getType_title() {
        return type_title;
    }

    public void setType_title(String type_title) {
        this.type_title = type_title;
    }

    public String getType_union() {
        return type_union;
    }

    public void setType_union(String type_union) {
        this.type_union = type_union;
    }

    public ExtendBean getType_extend() {
        return type_extend;
    }

    public void setType_extend(ExtendBean type_extend) {
        this.type_extend = type_extend;
    }

    public String getChildids() {
        return childids;
    }

    public void setChildids(String childids) {
        this.childids = childids;
    }

    public List<CardBean> getClasses() {
        return classes;
    }

    public void setClasses(List<CardBean> classes) {
        this.classes = classes;
    }

    @Override
    public String getTypeName() {
        return type_name;
    }

    @Override
    public int getTypeId() {
        return type_id;
    }

    @Override
    public ExtendBean getExtend() {
        return type_extend;
    }
}
