package com.akrivonos.a2chparser.dagger.components

import com.akrivonos.a2chparser.DvachApplication
import com.akrivonos.a2chparser.dagger.modules.AppModule
import com.akrivonos.a2chparser.dagger.modules.DatabaseModule
import com.akrivonos.a2chparser.dagger.modules.NetworkModule
import com.akrivonos.a2chparser.dagger.modules.ViewModelModule
import com.akrivonos.a2chparser.dagger.modules.builders.ActivityBuildersModule
import com.akrivonos.a2chparser.dagger.modules.builders.FragmentsBuilderModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    AppModule::class,
    ActivityBuildersModule::class,
    FragmentsBuilderModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<DvachApplication>{

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<DvachApplication>()
}