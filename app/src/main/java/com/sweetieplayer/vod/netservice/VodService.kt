package com.sweetieplayer.vod.netservice

import com.sweetieplayer.av.CheckVodTrySeeBean
import com.sweetieplayer.vod.ApiConfig
import com.sweetieplayer.vod.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface VodService {
    @GET(ApiConfig.getVod)
    fun getVideoDetail(@Query("vod_id") vod_id: Int, @Query("rel_limit") rel_limit: Int): Observable<BaseResult<VodBean>>

    @GET(ApiConfig.getVodList)
    fun getSameTypeList(@Query("type") type: Int, @Query("class") zlass: String, @Query("page") page: Int, @Query("limit") limit: Int): Observable<BaseResult<Page<VodBean>>>

    @GET(ApiConfig.getVodList)
    fun getSameActorList(@Query("type") type: Int, @Query("actor") actor: String, @Query("page") page: Int, @Query("limit") limit: Int): Observable<BaseResult<Page<VodBean>>>

    @GET(ApiConfig.COMMENT)
    fun getCommentList(@Query("rid") rid: Int, @Query("mid") mid: String, @Query("page") page: Int, @Query("limit") limit: Int): Observable<BaseResult<Page<CommentBean>>>

    @FormUrlEncoded
    @POST(ApiConfig.COMMENT)
    fun comment(
            @Field("comment_content") comment_content: String,
            @Field("comment_mid") comment_mid: String,
            @Field("comment_rid") comment_rid: String
    ): Observable<BaseResult<GetScoreBean>>
    @FormUrlEncoded
    @POST(ApiConfig.COMMENT)
    fun replayComment(
            @Field("comment_content") comment_content: String,
            @Field("comment_mid") comment_mid: String,
            @Field("comment_rid") comment_rid: String,
            @Field("comment_id") comment_id: String,
            @Field("comment_pid") comment_pid: String
    ): Observable<BaseResult<String>>

    @GET(ApiConfig.getVodList)
    fun getVodList(@Query("type") type: Int, @Query("page") page: Int, @Query("limit") limit: Int, @Query("class") zlass: String): Observable<PageResult<VodBean>>

    @GET(ApiConfig.getVodList)
    fun getVodList(@Query("page") page: Int, @Query("limit") limit: Int, @Query("wd") wd: String): Observable<PageResult<VodBean>>

    @FormUrlEncoded
    @POST(ApiConfig.LOGIN)
    fun login(@Field("user_name") user_name: String, @Field("user_pwd") user_pwd: String): Observable<BaseResult<String>>

    @FormUrlEncoded
    @POST(ApiConfig.REGISTER)
    fun register(@Field("user_name") user_name: String, @Field("user_pwd") user_pwd: String, @Field("user_pwd2") user_pwd2: String): Observable<BaseResult<String>>

    @FormUrlEncoded
    @POST(ApiConfig.REGISTER)
    fun registerByCode(@Field("to") to: String, @Field("user_pwd") user_pwd: String, @Field("code") code: String, @Field("ac") ac: String = "phone"): Observable<BaseResult<String>>

    @GET(ApiConfig.VERIFY_CODE)
    fun sendVerifyCode(@Query("to") to: String, @Query("ac") ac: String = "phone"): Observable<BaseResult<String>>

    @GET(ApiConfig.OPEN_REGISTER)
    fun openRegister(): Observable<BaseResult<OpenRegister>>

    @GET(ApiConfig.USER_INFO)
    fun userInfo(): Observable<BaseResult<UserInfoBean>>

    @POST(ApiConfig.SIGN)
    fun sign(): Observable<BaseResult<GetScoreBean>>

    @GET(ApiConfig.GROUP_CHAT)
    fun groupChat(): Observable<BaseResult<GroupChatBean>>

    @GET(ApiConfig.PAY_TIP)
    fun payTip(): Observable<BaseResult<PayTipBean>>

    @GET(ApiConfig.GOLD_TIP)
    fun goldTip(): Observable<BaseResult<GoldTipBean>>

    @FormUrlEncoded
    @POST(ApiConfig.CARD_BUY)
    fun cardBuy(@Field("card_pwd") card_pwd: String): Observable<BaseResult<CardBuyBean>>

    @FormUrlEncoded
    @POST(ApiConfig.UPGRADE_GROUP)
    fun upgradeGroup(@Field("long") long: String, @Field("group_id") group_id: String): Observable<BaseResult<CardBuyBean>>

    @GET(ApiConfig.SCORE_LIST)
    fun getScoreList(): Observable<BaseResult<ScoreListBean>>

    @POST(ApiConfig.CHANGE_AGENTS)
    fun changeAgents(): Observable<BaseResult<ChangeAgentsBean>>

    @GET(ApiConfig.AGENTS_SCORE)
    fun getAgentsScore(): Observable<BaseResult<AgentsScoreBean>>

    @FormUrlEncoded
    @POST(ApiConfig.POINT_PURCHASE)
    fun pointPurchase(@Field("price") price: String): Observable<BaseResult<PointPurchseBean>>

    @DELETE(ApiConfig.LOGOUT)
    fun logout(): Observable<BaseResult<String>>

    @FormUrlEncoded
    @POST(ApiConfig.CHANGE_NICKNAME)
    fun changeNickname(@Field("user_nick_name") user_nick_name: String): Observable<BaseResult<String>>

    @Multipart
    @POST(ApiConfig.CHANGE_AVATOR)
    fun changeAvator(@Part body: MultipartBody.Part): Observable<BaseResult<ChangeAvatorBean>>

    @FormUrlEncoded
    @POST(ApiConfig.GOLD_WITHDRAW)
    fun goldWithdrawApply(@Field("num") num: String, @Field("type") type: String, @Field("account") account: String, @Field("realname") realname: String): Observable<BaseResult<GoldWithdrawBean>>

    @GET(ApiConfig.GOLD_WITHDRAW)
    fun getGoldWithdrawRecord(@Query("page") page: String, @Query("limit") limit: String): Observable<BaseResult<Page<GoldWithdrawRecordBean>>>

    @GET(ApiConfig.FEEDBACK)
    fun getFeedbackList(@Query("page") page: String, @Query("limit") limit: String): Observable<BaseResult<Page<FeedbackBean>>>

    @FormUrlEncoded
    @POST(ApiConfig.FEEDBACK)
    fun feedbak(
            @Field("gbook_content") gbook_content: String
    ): Observable<BaseResult<String>>


    @FormUrlEncoded
    @POST(ApiConfig.COLLECTION)
    fun collect(
            @Field("mid") mid: String,
            @Field("id") id: String,
            @Field("type") type: String
    ): Observable<BaseResult<String>>

    @GET(ApiConfig.COLLECTION_LIST)
    fun getCollectList(
            @Query("page") page: String,
            @Query("limit") limit: String,
            @Query("ulog_type") ulog_type: String
    ): Observable<BaseResult<Page<CollectionBean>>>

    @DELETE(ApiConfig.COLLECTION)
    fun deleteCollect(
            @Query("ids") ids: String,
            @Query("type") type: String
    ): Observable<BaseResult<String>>

    @POST(ApiConfig.SHARE_SCORE)
    fun shareScore(): Observable<BaseResult<ShareBean>>

    @FormUrlEncoded
    @POST(ApiConfig.SCORE)
    fun score(
            @Field("id") id: String,
            @Field("score") type: String
    ): Observable<BaseResult<GetScoreBean>>

    @GET(ApiConfig.TASK_LIST)
    fun getTaskList(): Observable<BaseResult<TaskBean>>

    @GET(ApiConfig.MSG_LIST)
    fun getMsgList(): Observable<BaseResult<MessageBean>>

    @GET(ApiConfig.MSG_DETAIL)
    fun getMsgDetail(@Query("id") id: String): Observable<BaseResult<MessageDetail>>

    @GET(ApiConfig.EXPAND_CENTER)
    fun expandCenter(): Observable<BaseResult<ExpandCenter>>

    @GET(ApiConfig.MY_EXPAND)
    fun myExpand(@Query("page") page: String, @Query("limit") limit: String): Observable<BaseResult<MyExpand>>

    @FormUrlEncoded
    @POST(ApiConfig.SEND_DANMU)
    fun sendDanmu(
            @Field("content") content: String,
            @Field("vod_id") vod_id: String,
            @Field("at_time") at_time: String
    ): Observable<BaseResult<GetScoreBean>>

    @GET(ApiConfig.CHECK_VOD_TRYSEE)
    fun checkVodTrySee(
            @Query("id") id: String,
            @Query("mid") mid: String,
            @Query("nid") nid: String
    ): Observable<BaseResult<CheckVodTrySeeBean>>

    @FormUrlEncoded
    @POST(ApiConfig.BUY_VIDEO)
    fun buyVideo(
            @Field("type") type: String,
            @Field("id") id: String,
            @Field("sid") sid: String,
            @Field("nid") nid: String,
            @Field("mid") mid: String
    ): Observable<BaseResult<String>>

    @GET(ApiConfig.CHECK_VERSION)
    fun checkVersion(
            @Query("version") version: String,
            @Query("os") os: String
    ): Observable<BaseResult<AppUpdateBean>>

    @GET(ApiConfig.ORDER)
    fun order(
            @Query("order_code") order_code: String
    ): Observable<BaseResult<OrderBean>>

    @GET(ApiConfig.APP_CONFIG)
    fun getPlayAd(
            @Query("type") type: String
    ): Observable<BaseResult<AppConfigBean>>

    @GET(ApiConfig.SHARE_INFO)
    fun getShareInfo(
    ): Observable<BaseResult<ShareInfoBean>>

//    @FormUrlEncoded
//    @POST(ApiConfig.addPlayLog)
//    fun addPlayLog(@Field("vod_id") vod_id: String, @Field("nid") nid: String, @Field("source") source:String, @Field("percent") percent: String): Observable<BaseResult<String>>

    @FormUrlEncoded
    @POST(ApiConfig.addPlayLog)
    fun addPlayLog(@Field("vod_id") vod_id: String, @Field("nid") nid: String, @Field("source") source: String, @Field("percent") percent: String, @Field("urlIndex") urlIndex: String, @Field("curProgress") curProgress: String, @Field("playSourceIndex") playSourceIndex: String): Observable<BaseResult<UserVideo>>

    @GET(ApiConfig.getVideoProgress)
    fun getVideoProgress(@Query("vod_id") vod_id: String, @Query("nid") nid: String, @Query("source") source: String): Observable<BaseResult<Long>>

    @FormUrlEncoded
    @POST(ApiConfig.video_count)
    fun videoCount(@Field("rid") rid: String, @Field("nid") nid: String): Observable<BaseResult<String>>

    @FormUrlEncoded
    @POST(ApiConfig.watchTimeLong)
    fun addWatchTime(@Field("view_seconds") view_seconds: Int): Observable<BaseResult<GetScoreBean>>


    @GET(ApiConfig.getPlayLogList)
    fun getPlayLogList(@Query("page") page: String,
                       @Query("limit") limit: String): Observable<BaseResult<Page<PlayLogBean>>>

    @FormUrlEncoded
    @POST(ApiConfig.dleltePlayLogList)
    fun deletePlayLogList(
            @Field("id") id: String
    ): Observable<BaseResult<String>>

    @GET(ApiConfig.tabThreeName)
    fun getTabThreeName(): Observable<BaseResult<String>>

    @GET(ApiConfig.tabFourInfo)
    fun getTabFourInfo(): Observable<BaseResult<TabFourInfo>>

    @GET(ApiConfig.getRankList)
    fun getRankList(@Query("order_by") order_by: String,@Query("type") type: String,@Query("page") page: String,@Query("limit") limit: String): Observable<BaseResult<RankBean>>
}
