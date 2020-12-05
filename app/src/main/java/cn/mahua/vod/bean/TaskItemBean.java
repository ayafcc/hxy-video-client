package cn.mahua.vod.bean;

import cn.mahua.vod.R;

public class TaskItemBean {
    public int id = 1;
    public int image = 0;
    private String status;
    public int finish = 0;
    private String points;
    private String reward_num;
    private String title;
    private String info;

    public TaskItemBean(TaskBean.ShareBean bean) {
        id = 6;
        image = R.drawable.icon_task_daily_share;
        status = bean.getStatus();
        points = bean.getReward().getPoints();
        reward_num = bean.getReward_num();
        title = bean.getTitle();
        info = bean.getInfo();
        finish = bean.getFinish();
    }

    public TaskItemBean(TaskBean.SignBean bean) {
        id = 1;
        image = R.drawable.icon_task_sign;
        status = bean.getStatus();
        points = bean.getReward().getPoints();
        title = bean.getTitle();
        info = bean.getInfo();
        finish = bean.getFinish();
    }

    public TaskItemBean(TaskBean.CommentBean bean) {
        id = 4;
        image = R.drawable.icon_task_comment;
        status = bean.getStatus();
        points = bean.getReward().getPoints();
        reward_num = bean.getReward_num();
        title = bean.getTitle();
        info = bean.getInfo();
        finish = bean.getFinish();
    }

    public TaskItemBean(TaskBean.DanmuBean bean) {
        id = 3;
        image = R.drawable.icon_task_danmu;
        status = bean.getStatus();
        points = bean.getReward().getPoints();
        reward_num = bean.getReward_num();
        title = bean.getTitle();
        info = bean.getInfo();
        finish = bean.getFinish();
    }

    public TaskItemBean(TaskBean.MarkBean bean) {
        id = 2;
        image = R.drawable.icon_task_look;
        status = bean.getStatus();
        points = bean.getReward().getPoints();
        reward_num = bean.getReward_num();
        title = bean.getTitle();
        info = bean.getInfo();
        finish = bean.getFinish();
    }

    public TaskItemBean(TaskBean.View30mBean bean) {
        id = 5;
        image = R.drawable.icon_task_time;
        status = bean.getStatus();
        points = bean.getReward().getPoints();
        reward_num = bean.getReward_num();
        title = bean.getTitle();
        info = bean.getInfo();
        finish = bean.getFinish();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getReward_num() {
        return reward_num;
    }

    public void setReward_num(String reward_num) {
        this.reward_num = reward_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
