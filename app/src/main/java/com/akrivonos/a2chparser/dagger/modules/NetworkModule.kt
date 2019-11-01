package com.akrivonos.a2chparser.dagger.modules

import androidx.annotation.NonNull
import com.akrivonos.a2chparser.retrofit.RetrofitSearch
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @NonNull
    @Singleton
    fun provideRetrofit(): RetrofitSearch = RetrofitSearch()
}