package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BannerService {
    @GET(ApiConfig.getBannerList)
    Observable<PageResult<VodBean>> getBannerList(@Query("level") int level);

    @GET(ApiConfig.getBannerList)
    Observable<PageResult<VodBean>> getBannerList(@Query("type") int type_id, @Query("level") int level);
}
