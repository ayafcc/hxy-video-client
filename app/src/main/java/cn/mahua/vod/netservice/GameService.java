package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.GameBean;
import cn.mahua.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GameService {
    @GET(ApiConfig.getGameList)
    Observable<PageResult<GameBean>> getGameList(@Query("limit") String limit, @Query("page") String page,@Query("size") String size);
}
