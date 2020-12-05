package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.CommentBean;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.BaseResult;
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