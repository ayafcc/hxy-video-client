package com.sweetieplayer.vod.netservice;


import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.CardBean;
import com.sweetieplayer.vod.bean.PageResult;
import com.sweetieplayer.vod.bean.RecommendBean2;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardService {
    @GET(ApiConfig.getCardList)
    Observable<PageResult<CardBean>> getCardList(@Query("need_vod") boolean need_vod);

    @GET(ApiConfig.getRecommendList)
    Observable<PageResult<RecommendBean2>> getRecommendList();
}
