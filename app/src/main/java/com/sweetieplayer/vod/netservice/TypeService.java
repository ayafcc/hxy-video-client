package com.sweetieplayer.vod.netservice;

import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.TypeBean;
import com.sweetieplayer.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TypeService {
    @GET(ApiConfig.getTypeList)
    Observable<PageResult<TypeBean>> getTypeList();
}
