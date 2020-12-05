package cn.mahua.vod.netservice;


import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.TypeBean;
import cn.mahua.vod.bean.BaseResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardMoreService {
    @GET(ApiConfig.getCardListByType)
    Observable<BaseResult<TypeBean>> getCardListByType(@Query("type_id") int type_id);
}
