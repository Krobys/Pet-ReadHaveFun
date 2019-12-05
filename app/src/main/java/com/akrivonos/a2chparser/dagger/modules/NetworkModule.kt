package com.akrivonos.a2chparser.dagger.modules

import com.akrivonos.a2chparser.BuildConfig
import com.akrivonos.a2chparser.retrofit.ApiRetrofitInterface
import com.akrivonos.a2chparser.retrofit.RxRetryCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxRetryCallAdapterFactory.create())
                .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiRetrofitInterface(retrofit: Retrofit): ApiRetrofitInterface {
        return retrofit.create(ApiRetrofitInterface::class.java)
    }

}