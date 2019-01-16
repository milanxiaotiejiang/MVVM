package com.huaqing.property.ext.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.huaqing.property.common.helper.RxSchedulers
import io.reactivex.*
import io.reactivex.android.MainThreadDisposable

fun <T> LiveData<T>.toReactiveStream(observerScheduler: Scheduler = RxSchedulers.ui): Flowable<T> =
    Flowable.create(
        { emitter: FlowableEmitter<T> ->
            val observer = Observer<T> { data ->
                data?.let {
                    emitter.onNext(it)
                }
            }
            observeForever(observer)

            emitter.setCancellable {
                object : MainThreadDisposable() {
                    override fun onDispose() {
                        removeObserver(observer)
                    }
                }
            }
        },
        BackpressureStrategy.LATEST
    )
        .subscribeOn(RxSchedulers.ui)
        .observeOn(observerScheduler)


fun <X, Y> LiveData<X>.map(function: (X) -> Y): LiveData<Y> =
    Transformations.map(this, function)

fun <X, Y> LiveData<X>.switchMap(function: (X) -> LiveData<Y>): LiveData<Y> =
    Transformations.switchMap(this, function)