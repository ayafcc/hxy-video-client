package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.PageResult;
import cn.mahua.vod.bean.SpecialtTopicBean;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.Map;

public interface TopicService {
    @GET(ApiConfig.getTopicList)
    Observable<PageResult<SpecialtTopicBean>> getTopicList();
}
