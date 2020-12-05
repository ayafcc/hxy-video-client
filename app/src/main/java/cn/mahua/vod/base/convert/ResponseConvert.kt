package com.github.StormWyrm.wanandroid.base.net.convert

import cn.mahua.vod.bean.BaseResult
import com.github.StormWyrm.wanandroid.base.exception.ApiException
import io.reactivex.functions.Function

class ResponseConvert<T> : Function<BaseResult<T>, T> {
    /**
     * Apply some calculation to the input value and return some other value.
     * @param t the input value
     * @return the output value
     * @throws Exception on error
     */
    override fun apply(t: BaseResult<T>): T {
        if (t.code != 200) {
            throw ApiException(t.code, t.msg)
        }
        var data = t.data
        if (t.data == null) {
            data = "" as T
        }
        return data
    }
}