package com.sweetieplayer.vod.netservice;

import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.StartBean;
import com.sweetieplayer.vod.bean.BaseResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface StartService {
    @GET(ApiConfig.getStart)
    Observable<BaseResult<StartBean>> getStartBean();

//    @GET(ApiConfig.getStart)
//    Observable<BaseResult<Object>> getStartBean();
}
