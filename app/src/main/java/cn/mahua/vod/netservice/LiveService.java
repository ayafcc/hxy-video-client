package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.LiveBean;
import cn.mahua.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LiveService {
    @GET(ApiConfig.getLiveList)
    Observable<PageResult<LiveBean>> getLiveList();
}
