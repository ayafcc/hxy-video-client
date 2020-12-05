package com.github.StormWyrm.wanandroid.base.sheduler

import io.reactivex.*
import org.reactivestreams.Publisher

open class BaseScheduler<T> protected constructor(
    private val subscribeOnScheduler: Scheduler,
    private val observerOnSchedulder: Scheduler
) : ObservableTransformer<T, T>,
    SingleTransformer<T, T>,
    MaybeTransformer<T, T>,
    CompletableTransformer,
    FlowableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observerOnSchedulder)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observerOnSchedulder)
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observerOnSchedulder)
    }

    override fun apply(upstream: Completable): CompletableSource {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observerOnSchedulder)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observerOnSchedulder)
    }

}