package cn.mahua.vod.netservice;


import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.CardBean;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.RecommendBean2;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardService {
    @GET(ApiConfig.getCardList)
    Observable<PageResult<CardBean>> getCardList(@Query("need_vod") boolean need_vod);

    @GET(ApiConfig.getRecommendList)
    Observable<PageResult<RecommendBean2>> getRecommendList();
}
