package cn.mahua.vod.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@SuppressWarnings("unused")
public class RetryWhen implements Function<Observable<Throwable>, ObservableSource<?>> {

    private static final String TAG = "RetryWhen";

    private long count = 0;
    private long maxCount = 1;
    private int trySeconds = 5;

    public RetryWhen() {
    }

    public RetryWhen(long maxCount) {
        this.maxCount = maxCount;
    }

    public RetryWhen(long maxCount, int trySeconds) {
        this.maxCount = maxCount;
        this.trySeconds = trySeconds;
    }

    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) {
        return throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
            //等于-1的时候无限重试
            if (maxCount == -1) {
                return Observable.timer(trySeconds, TimeUnit.SECONDS);
            } else {
                if (count <= maxCount) {
                    Log.e(TAG, "请求超时，开始第" + count+ "次重试！");
                    count++;
                    return Observable.timer(trySeconds, TimeUnit.SECONDS);
                } else {
                    return Observable.error(new Throwable("retryWhen终止啦"));
                }
            }
        });
    }
}
