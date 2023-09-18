package com.sweetieplayer.vod.netservice;

import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.PageResult;
import com.sweetieplayer.vod.bean.SpecialtTopicBean;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TopicService {
    @GET(ApiConfig.getTopicList)
    Observable<PageResult<SpecialtTopicBean>> getTopicList();
}
