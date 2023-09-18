package com.github.StormWyrm.wanandroid.base.net.observer

import com.blankj.utilcode.util.ToastUtils
import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<E>(private val showErrorTip: Boolean = true) : Observer<E> {
    private lateinit var disposable: Disposable

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(data: E) {
        onSuccess(data)
    }

    override fun onError(e: Throwable) {
        val responseException: ResponseException = e as ResponseException
        if (showErrorTip) {
            ToastUtils.showShort(responseException.getErrorMessage())
        }
        onError(responseException)
    }

    override fun onComplete() {

    }

    fun getDisposable() = disposable

    abstract fun onSuccess(data: E)

    abstract fun onError(e: ResponseException)
}