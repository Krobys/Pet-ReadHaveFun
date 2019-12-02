package com.rxchainretrier.network

import android.app.Application
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.akrivonos.a2chparser.base.BaseActivity
import com.akrivonos.a2chparser.dagger.components.DaggerAppComponent
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import timber.log.Timber
import java.lang.reflect.Type

class RxRetryCallAdapter<R>(private val originalAdapter : CallAdapter<R, *>) : CallAdapter<R, Any> {
    private val retrySubject = DaggerAppComponent.builder().build().getAppSubject()

    override fun adapt(call : Call<R>) : Any {
        return when (val adaptedValue = originalAdapter.adapt(call)) {
            is Completable -> {
                adaptedValue.doOnError(this::sendBroadcast)
                        .retryWhen {
                            retrySubject.toFlowable(BackpressureStrategy.LATEST)
                                    .observeOn(Schedulers.io())
                        }
            }
            is Single<*> -> {
                adaptedValue.doOnError(this::sendBroadcast)
                        .retryWhen {
                            retrySubject.toFlowable(BackpressureStrategy.LATEST)
                                    .observeOn(Schedulers.io())
                        }
            }
            is Maybe<*> -> {
                adaptedValue.doOnError(this::sendBroadcast)
                        .retryWhen {
                            retrySubject.toFlowable(BackpressureStrategy.LATEST)
                                    .observeOn(Schedulers.io())
                        }
            }
            is Observable<*> -> {
                adaptedValue.doOnError(this::sendBroadcast)
                        .retryWhen {
                            retrySubject
                                    .observeOn(Schedulers.io())
                        }
            }
            is Flowable<*> -> {
                adaptedValue.doOnError(this::sendBroadcast)
                        .retryWhen {
                            retrySubject.toFlowable(BackpressureStrategy.LATEST)
                                    .observeOn(Schedulers.io())
                        }
            }
            else -> {
                adaptedValue
            }
        }
    }

    override fun responseType() : Type = originalAdapter.responseType()

    private fun sendBroadcast(throwable : Throwable) {
        val app : Application = DaggerAppComponent.builder().build().getAppInstance()
        Timber.e(throwable)
        LocalBroadcastManager.getInstance(app).sendBroadcast(Intent(BaseActivity.ERROR_ACTION))
    }
}