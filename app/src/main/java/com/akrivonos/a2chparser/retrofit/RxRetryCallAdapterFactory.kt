package com.akrivonos.a2chparser.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class RxRetryCallAdapterFactory : CallAdapter.Factory() {
    companion object {
        fun create() : CallAdapter.Factory = RxRetryCallAdapterFactory()
    }

    private val originalFactory = RxJava2CallAdapterFactory.create()

    override fun get(returnType : Type, annotations : Array<Annotation>, retrofit : Retrofit) : CallAdapter<*, *>? {
        val adapter : CallAdapter<*, *> = originalFactory.get(returnType, annotations, retrofit) ?: return null
        return RxRetryCallAdapter(adapter)
    }

    fun <M>returnAdapter(adapter: CallAdapter<M, *>): RxRetryCallAdapter<M> {
        return RxRetryCallAdapter<M>(adapter)
    }
}