package cn.mahua.vod.netservice;

import cn.mahua.vod.ApiConfig;
import cn.mahua.vod.bean.TypeBean;
import cn.mahua.vod.bean.PageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TypeService {
    @GET(ApiConfig.getTypeList)
    Observable<PageResult<TypeBean>> getTypeList();
}
