package com.akrivonos.a2chparser.dagger.components

import android.app.Application
import com.akrivonos.a2chparser.DvachApplication
import com.akrivonos.a2chparser.dagger.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, DatabaseModule::class, NetworkModule::class,
    AppModule::class, ActivityBuildersModule::class, FragmentsBuilderModule::class,
    ViewModelModule::class])
interface AppComponent {
    fun inject(application : DvachApplication)

    fun getAppInstance() : Application

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: DvachApplication) : Builder

        fun build() : AppComponent
    }
}