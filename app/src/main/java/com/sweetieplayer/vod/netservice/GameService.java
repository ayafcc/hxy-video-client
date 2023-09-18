package com.sweetieplayer.vod.netservice;

import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.GameBean;
import com.sweetieplayer.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GameService {
    @GET(ApiConfig.getGameList)
    Observable<PageResult<GameBean>> getGameList(@Query("limit") String limit, @Query("page") String page,@Query("size") String size);
}
