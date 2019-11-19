package com.akrivonos.a2chparser.dagger.modules

import com.akrivonos.a2chparser.retrofit.RetrofitSearch
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): RetrofitSearch = RetrofitSearch()

    @Provides
    fun provideSharedPreferenceUtils(): SharedPreferenceUtils = SharedPreferenceUtils()

    @Provides
    fun provideItemDecorator() : ItemDecoratorUtils = ItemDecoratorUtils()

}