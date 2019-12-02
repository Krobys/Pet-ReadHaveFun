package com.akrivonos.a2chparser.dagger.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
object AppModule {
    private val subject = PublishSubject.create<Unit>()

    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(application: Application): Context{
        return application
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrySubject() : PublishSubject<Unit> = subject
}