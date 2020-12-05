package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopService {
    @GET(ApiConfig.getTopList)
    Observable<PageResult<VodBean>> getTopList(@Query("request_type") String request_type, @Query("limit") int limit, @Query("page") int page);

    @GET(ApiConfig.getTopList)
    Observable<PageResult<VodBean>> getTopList(@Query("type") int type_id, @Query("request_type") String request_type, @Query("limit") int limit, @Query("page") int page);
}
