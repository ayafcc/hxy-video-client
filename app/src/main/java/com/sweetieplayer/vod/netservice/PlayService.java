package com.sweetieplayer.vod.netservice;

import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.CommentBean;
import com.sweetieplayer.vod.bean.VodBean;
import com.sweetieplayer.vod.bean.PageResult;
import com.sweetieplayer.vod.bean.BaseResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlayService {

    @GET(ApiConfig.getVod)
    Observable<BaseResult<VodBean>> getVod(@Query("vod_id") int vod_id, @Query("rel_limit") int rel_limit);

    @GET(ApiConfig.getVodList)
    Observable<PageResult<VodBean>> getSameTypeList(@Query("type") int type, @Query("class") String zlass, @Query("page") int page, @Query("limit") int limit);

    @GET(ApiConfig.getVodList)
    Observable<PageResult<VodBean>> getSameActorList(@Query("type") int type, @Query("actor") String actor,  @Query("page") int page, @Query("limit") int limit);

    @GET(ApiConfig.COMMENT)
    Observable<PageResult<CommentBean>> getCommentList(@Query("rid") int type, @Query("mid") String mid, @Query("page") int page, @Query("limit") int limit);

}