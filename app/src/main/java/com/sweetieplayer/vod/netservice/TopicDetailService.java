package com.sweetieplayer.vod.netservice;

import com.sweetieplayer.vod.ApiConfig;
import com.sweetieplayer.vod.bean.BaseResult;
import com.sweetieplayer.vod.bean.TopicDetailBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopicDetailService {
    @GET(ApiConfig.getTopicDetail)
    Observable<BaseResult<TopicDetailBean>> getTopicDetail(@Query("topic_id") String topic_id);
}
