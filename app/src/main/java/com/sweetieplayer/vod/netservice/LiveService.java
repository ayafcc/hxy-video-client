package com.sweetieplayer.vod.netservice;

import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.LiveBean;
import com.sweetieplayer.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LiveService {
    @GET(ApiConfig.getLiveList)
    Observable<PageResult<LiveBean>> getLiveList();
}
