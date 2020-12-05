package cn.mahua.vod.bean;

public class TaskBean {


    /**
     * sign : {"status":"1","reward":{"points":"10"},"title":"签到","info":"点击签到即可获得积分","finish":0}
     * article : {"title":null,"status":null}
     * comment : {"status":"1","reward":{"points":"10"},"reward_num":"1","title":"评论","info":"为视频即可获得积分","finish":1}
     * dianzan : {"status":null,"reward":{"points":null},"title":"点赞","info":"为视频点赞即可获得积分","finish":0}
     * danmu : {"status":"1","reward":{"points":"10"},"reward_num":"1","title":"弹幕","info":"发送弹幕即可获得积分","finish":1}
     * mark : {"status":"1","reward":{"points":"10"},"reward_num":"1","title":"评分","info":"为视频评分即可获得积分","finish":1}
     * share : {"status":"1","reward":{"points":"10"},"reward_num":"1","title":"分享","info":"分享好友即可获得积分","finish":1}
     * view30m : {"status":"1","reward":{"points":"10"},"reward_num":"1","title":"观影30分钟","info":"观影30分钟即可获得积分","finish":0}
     */

    private SignBean sign;
    private ArticleBean article;
    private CommentBean comment;
    private DianzanBean dianzan;
    private DanmuBean danmu;
    private MarkBean mark;
    private ShareBean share;
    private View30mBean view30m;

    public SignBean getSign() {
        return sign;
    }

    public void setSign(SignBean sign) {
        this.sign = sign;
    }

    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public DianzanBean getDianzan() {
        return dianzan;
    }

    public void setDianzan(DianzanBean dianzan) {
        this.dianzan = dianzan;
    }

    public DanmuBean getDanmu() {
        return danmu;
    }

    public void setDanmu(DanmuBean danmu) {
        this.danmu = danmu;
    }

    public MarkBean getMark() {
        return mark;
    }

    public void setMark(MarkBean mark) {
        this.mark = mark;
    }

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public View30mBean getView30m() {
        return view30m;
    }

    public void setView30m(View30mBean view30m) {
        this.view30m = view30m;
    }

    public static class SignBean {
        /**
         * status : 1
         * reward : {"points":"10"}
         * title : 签到
         * info : 点击签到即可获得积分
         * finish : 0
         */

        private String status;
        private RewardBean reward;
        private String title;
        private String info;
        private int finish;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RewardBean getReward() {
            return reward;
        }

        public void setReward(RewardBean reward) {
            this.reward = reward;
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

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public static class RewardBean {
            /**
             * points : 10
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }
    }

    public static class ArticleBean {
        /**
         * title : null
         * status : null
         */

        private Object title;
        private Object status;

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }
    }

    public static class CommentBean {
        /**
         * status : 1
         * reward : {"points":"10"}
         * reward_num : 1
         * title : 评论
         * info : 为视频即可获得积分
         * finish : 1
         */

        private String status;
        private RewardBeanX reward;
        private String reward_num;
        private String title;
        private String info;
        private int finish;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RewardBeanX getReward() {
            return reward;
        }

        public void setReward(RewardBeanX reward) {
            this.reward = reward;
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

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public static class RewardBeanX {
            /**
             * points : 10
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }
    }

    public static class DianzanBean {
        /**
         * status : null
         * reward : {"points":null}
         * title : 点赞
         * info : 为视频点赞即可获得积分
         * finish : 0
         */

        private Object status;
        private RewardBeanXX reward;
        private String title;
        private String info;
        private int finish;

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public RewardBeanXX getReward() {
            return reward;
        }

        public void setReward(RewardBeanXX reward) {
            this.reward = reward;
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

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public static class RewardBeanXX {
            /**
             * points : null
             */

            private Object points;

            public Object getPoints() {
                return points;
            }

            public void setPoints(Object points) {
                this.points = points;
            }
        }
    }

    public static class DanmuBean {
        /**
         * status : 1
         * reward : {"points":"10"}
         * reward_num : 1
         * title : 弹幕
         * info : 发送弹幕即可获得积分
         * finish : 1
         */

        private String status;
        private RewardBeanXXX reward;
        private String reward_num;
        private String title;
        private String info;
        private int finish;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RewardBeanXXX getReward() {
            return reward;
        }

        public void setReward(RewardBeanXXX reward) {
            this.reward = reward;
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

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public static class RewardBeanXXX {
            /**
             * points : 10
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }
    }

    public static class MarkBean {
        /**
         * status : 1
         * reward : {"points":"10"}
         * reward_num : 1
         * title : 评分
         * info : 为视频评分即可获得积分
         * finish : 1
         */

        private String status;
        private RewardBeanXXXX reward;
        private String reward_num;
        private String title;
        private String info;
        private int finish;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RewardBeanXXXX getReward() {
            return reward;
        }

        public void setReward(RewardBeanXXXX reward) {
            this.reward = reward;
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

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public static class RewardBeanXXXX {
            /**
             * points : 10
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }
    }

    public static class ShareBean {
        /**
         * status : 1
         * reward : {"points":"10"}
         * reward_num : 1
         * title : 分享
         * info : 分享好友即可获得积分
         * finish : 1
         */

        private String status;
        private RewardBeanXXXXX reward;
        private String reward_num;
        private String title;
        private String info;
        private int finish;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RewardBeanXXXXX getReward() {
            return reward;
        }

        public void setReward(RewardBeanXXXXX reward) {
            this.reward = reward;
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

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public static class RewardBeanXXXXX {
            /**
             * points : 10
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }
    }

    public static class View30mBean {
        /**
         * status : 1
         * reward : {"points":"10"}
         * reward_num : 1
         * title : 观影30分钟
         * info : 观影30分钟即可获得积分
         * finish : 0
         */

        private String status;
        private RewardBeanXXXXXX reward;
        private String reward_num;
        private String title;
        private String info;
        private int finish;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RewardBeanXXXXXX getReward() {
            return reward;
        }

        public void setReward(RewardBeanXXXXXX reward) {
            this.reward = reward;
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

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public static class RewardBeanXXXXXX {
            /**
             * points : 10
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }
    }
}
