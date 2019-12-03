package com.akrivonos.a2chparser.provider

import android.app.Application
import com.akrivonos.a2chparser.BuildConfig
import com.akrivonos.a2chparser.retrofit.ApiRetrofitInterface
import com.akrivonos.a2chparser.retrofit.RxRetryCallAdapterFactory
import io.reactivex.subjects.PublishSubject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppProvider {
    private val subject = PublishSubject.create<Unit>()

    private val apiService : ApiRetrofitInterface by lazy {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxRetryCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
        return@lazy retrofit.create(ApiRetrofitInterface::class.java)
    }

    lateinit var appInstance : Application

    fun provideRetrySubject() : PublishSubject<Unit> = subject

    fun provideApiService() : ApiRetrofitInterface = apiService
}