package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.VodBean;
import cn.mahua.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScreenService {

    @GET(ApiConfig.getVodList)
    Observable<PageResult<VodBean>> getVodList(@Query("type") int type, @Query("class") String zlass, @Query("lang") String lang, @Query("area") String area, @Query("year") String year,@Query("by") String by, @Query("page") int page, @Query("limit") int limit);

}
