package com.github.StormWyrm.wanandroid.base.net

import cn.mahua.vod.base.BaseActivity
import cn.mahua.vod.bean.BaseResult
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.base.net.convert.ExceptionConvert
import com.github.StormWyrm.wanandroid.base.net.convert.ResponseConvert
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.base.sheduler.IoMainScheduler
import io.reactivex.Observable

object RequestManager {
    @JvmStatic
    fun <T> execute(activity: BaseActivity, observable: Observable<BaseResult<T>>?, observer: BaseObserver<T>) {
        if (observable == null)
            throw RuntimeException("the observable is null")

        observable
            .map(ResponseConvert())
            .onErrorResumeNext(ExceptionConvert<T>())
            .compose(IoMainScheduler())
            .subscribe(observer)

        activity.addDisposable(observer.getDisposable())
    }

    @JvmStatic
    fun <T> execute(fragment: BaseFragment, observable: Observable<BaseResult<T>>?, observer: BaseObserver<T>) {
        if (observable == null)
            throw RuntimeException("the observable is null")

        observable
                .map(ResponseConvert())
                .onErrorResumeNext(ExceptionConvert<T>())
                .compose(IoMainScheduler())
                .subscribe(observer)
        fragment
    }
}