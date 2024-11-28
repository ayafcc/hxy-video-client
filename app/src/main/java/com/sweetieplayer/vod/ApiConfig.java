package com.sweetieplayer.vod;

public class ApiConfig {
    //public static final String AD_ID = "demo";
//    public static final String BASE_URL = "http://192.168.27.5";
    public static final String BASE_URL = "http://ihxy.me:8090";
    public static final String getStart = "/api.php/v1.main/startup";
    public static final String getTypeList = "/api.php/v1.vod/types";
    public static final String getBannerList = "/api.php/v1.vod";
//    public static final String codeurl = "http://192.168.27.5";
    public static final String codeurl = "http://74.120.170.55:8090";
    //专题
    public static final String getTopicList = "/api.php/v1.topic/topicList";
    //专题详情
    public static final String getTopicDetail = "/api.php/v1.topic/topicDetail";
    //游戏
    public static final String getGameList = "/api.php/v1.youxi/index";
    //添加视频播放记录
    public static final String addPlayLog = "/api.php/v1.user/addViewLog";
    //上报观影时长
    public static final String watchTimeLong = "/api.php/v1.user/viewSeconds";
    //获取视频播放记录
    public static final String getPlayLogList = "/api.php/v1.user/viewLog";
    //获取视频播放进度
    public static final String getVideoProgress = "/api.php/v1.vod/videoProgress";
    //删除播放记录
    public static final String dleltePlayLogList = "/api.php/v1.user/delVlog";
    //直播列表
    public static final String getLiveList = "/api.php/v1.zhibo";
    //直播详情
    public static final String getLiveDetail = "/api.php/v1.zhibo/detail";


    public static final String getTopList = "/api.php/v1.vod";
    public static final String getCardList = "/api.php/v1.main/category";
    public static final String getRecommendList = "/api.php/v1.vod/vodPhbAll";
    public static final String getCardListByType = "/api.php/v1.vod/type";
    public static final String getVodList = "/api.php/v1.vod";
    public static final String getVod = "/api.php/v1.vod/detail";

    public static final String COMMENT = "/api.php/v1.comment";
    public static final String USER_INFO = "/api.php/v1.user/detail";
    public static final String LOGIN = "/api.php/v1.auth/login";
    public static final String LOGOUT = "/api.php/v1.auth/logout";
    public static final String REGISTER = "/api.php/v1.auth/register";
    public static final String VERIFY_CODE = "/api.php/v1.auth/registerSms";
    public static final String OPEN_REGISTER = "/api.php/v1.user/phoneReg";
    public static final String SIGN = "/api.php/v1.sign";
    public static final String GROUP_CHAT = "/api.php/v1.groupchat";
    public static final String CARD_BUY = "/api.php/v1.user/buy";
    public static final String UPGRADE_GROUP = "/api.php/v1.user/group";
    public static final String SCORE_LIST = "/api.php/v1.user/groups";
    public static final String CHANGE_AGENTS = "/api.php/v1.user/changeAgents";
    public static final String AGENTS_SCORE = "/api.php/v1.user/agentsScore";
    public static final String POINT_PURCHASE = "/api.php/v1.user/order";
    public static final String CHANGE_NICKNAME = "/api.php/v1.user";
    public static final String CHANGE_AVATOR = "/api.php/v1.upload/user";
    public static final String GOLD_WITHDRAW = "/api.php/v1.user/goldWithdrawApply";
    public static final String PAY_TIP = "/api.php/v1.user/payTip";
    public static final String GOLD_TIP = "/api.php/v1.user/goldTip";
    public static final String FEEDBACK = "/api.php/v1.gbook";
    public static final String COLLECTION_LIST = "/api.php/v1.user/favs";
    public static final String COLLECTION = "/api.php/v1.user/ulog";
    public static final String SHARE_SCORE = "/api.php/v1.user/shareScore";
    public static final String TASK_LIST = "/api.php/v1.user/task";
    public static final String MSG_LIST = "/api.php/v1.message/index";
    public static final String MSG_DETAIL = "/api.php/v1.message/detail";
    public static final String EXPAND_CENTER = "/api.php/v1.user/userLevelConfig";
    public static final String MY_EXPAND = "/api.php/v1.user/subUsers";
    public static final String SEND_DANMU = "/api.php/v1.danmu";
    public static final String SCORE = "/api.php/v1.vod/score";
    public static final String CHECK_VOD_TRYSEE = "/api.php/v1.user/checkVodTrySee";
    public static final String BUY_VIDEO = "/api.php/v1.user/buypopedom";
    public static final String CHECK_VERSION = "/api.php/v1.main/version";
    public static final String PAY = "/api.php/v1.user/pay";
    public static final String ORDER = "/api.php/v1.user/order";
    public static final String APP_CONFIG = "/api.php/v1.user/appConfig";
    public static final String SHARE_INFO = "/api.php/v1.user/shareInfo";
    public static final String video_count = "/api.php/v1.vod/videoViewRecode";
    public static final String tabFourInfo = "/api.php/v1.youxi/index";
    public static final String tabThreeName = "/api.php/v1.zhibo/thirdUiName";
    public static final String getRankList = "/api.php/v1.vod/vodphb";
}
