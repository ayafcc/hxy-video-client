package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.BaseResult;
import cn.mahua.vod.bean.TopicDetailBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopicDetailService {
    @GET(ApiConfig.getTopicDetail)
    Observable<BaseResult<TopicDetailBean>> getTopicDetail(@Query("topic_id") String topic_id);
}
