package com.sweetieplayer.vod.netservice;


import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.TypeBean;
import com.sweetieplayer.vod.bean.BaseResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardMoreService {
    @GET(ApiConfig.getCardListByType)
    Observable<BaseResult<TypeBean>> getCardListByType(@Query("type_id") int type_id);
}
