package com.akrivonos.a2chparser.retrofit

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.akrivonos.a2chparser.provider.AppProvider
import com.rxchainretrier.base.BaseFragment.Companion.ERROR_ACTION
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import timber.log.Timber
import java.lang.reflect.Type

class RxRetryCallAdapter<R>(private val originalAdapter: CallAdapter<R, *>) : CallAdapter<R, Any> {
    override fun adapt(call: Call<R>): Any {
        val adaptedValue = originalAdapter.adapt(call)
        return when (adaptedValue) {
            is Completable -> {
                adaptedValue.doOnError(this::sendBroadcastError)
                        .retryWhen {
                            AppProvider.provideRetrySubject().toFlowable(BackpressureStrategy.LATEST)
                                    .observeOn(Schedulers.io())
                        }
            }
            is Single<*> -> {
                adaptedValue.doOnError(this::sendBroadcastError)
                        .retryWhen {
                            AppProvider.provideRetrySubject().toFlowable(BackpressureStrategy.LATEST)
                                    .observeOn(Schedulers.io())
                        }
            }
            is Maybe<*> -> {
                adaptedValue.doOnError(this::sendBroadcastError)
                        .retryWhen {
                            AppProvider.provideRetrySubject().toFlowable(BackpressureStrategy.LATEST)
                                    .observeOn(Schedulers.io())
                        }
            }
            is Observable<*> -> {
                adaptedValue.doOnError(this::sendBroadcastError)
                        .retryWhen {
                            AppProvider.provideRetrySubject()
                                    .observeOn(Schedulers.io())
                        }
            }
            is Flowable<*> -> {
                adaptedValue.doOnError(this::sendBroadcastError)
                        .retryWhen {
                            AppProvider.provideRetrySubject().toFlowable(BackpressureStrategy.LATEST)
                                    .observeOn(Schedulers.io())
                        }
            }
            else -> {
                adaptedValue
            }
        }
    }

    override fun responseType(): Type = originalAdapter.responseType()

    private fun sendBroadcastError(throwable: Throwable) {
        Timber.e(throwable)
        LocalBroadcastManager.getInstance(AppProvider.appInstance).sendBroadcast(Intent(ERROR_ACTION))
    }
}