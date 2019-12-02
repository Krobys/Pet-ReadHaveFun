package com.akrivonos.a2chparser

import androidx.multidex.MultiDexApplication
import com.akrivonos.a2chparser.dagger.AppInjector
import com.akrivonos.a2chparser.dagger.components.AppComponent
import com.akrivonos.a2chparser.dagger.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class DvachApplication : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
        appComponent.inject(this)
        AppInjector.init(this)
    }


    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}