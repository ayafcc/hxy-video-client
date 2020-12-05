package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.StartBean;
import cn.mahua.vod.bean.BaseResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface StartService {
    @GET(ApiConfig.getStart)
    Observable<BaseResult<StartBean>> getStartBean();

//    @GET(ApiConfig.getStart)
//    Observable<BaseResult<Object>> getStartBean();
}
